package com.ewaroenk

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ewaroenk.database.DatabaseHelper
import com.ewaroenk.model.ModelBarang
import kotlinx.android.synthetic.main.dialog_detail_barang.*


class DetailBarangDialog : BottomSheetDialogFragment() {

    private var dataBarang = ModelBarang()

    companion object {
        lateinit private var listeners: OnDialogItemClick

        fun newInstance(data: ModelBarang, listener: OnDialogItemClick): DetailBarangDialog {

            listeners = listener
            val detail = DetailBarangDialog()

            val bind = Bundle()
            bind.putParcelable("DATA", data)

            detail.arguments = bind
            return detail

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments

        if (args != null)
            dataBarang= args.getParcelable("DATA")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_detail_barang, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialogNama.text = dataBarang.nama.toUpperCase();
        dialogHarga.text = dataBarang.harga.toString();
        dialogStok.text = dataBarang.stok.toString();

        toolbarDialog.inflateMenu(R.menu.dialog_menu)
        toolbarDialog.setOnMenuItemClickListener {

            when (it.itemId) {

                R.id.dialogEdit -> {
                    listeners.dialogEditCallback(dataBarang)
                    dialog.dismiss()
                }
                R.id.dialogHapus -> {
                    val build = context?.let { it1 -> AlertDialog.Builder(it1) }
                    build?.setTitle("Hapus Data")
                    build?.setMessage("Apakah Kamu Ingin Menghapus Data ${dataBarang.nama}")
                    build?.setPositiveButton("HAPUS", { _, _ ->

                        val stas = DatabaseHelper.deleteData(dataBarang.id)

                        if (stas != 0) {
                            dialog.dismiss()
                            listeners.dialogDeleteCallback(dataBarang)

                            Toast.makeText(activity, "Berhasil Menghapus Data", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(activity, "Gagal Menghapus Data", Toast.LENGTH_SHORT).show()
                        }

                    })
                    build?.setNegativeButton("BATAL", null)
                    build?.create()?.show()
                }
            }
            true
        }
    }

    interface OnDialogItemClick {
        fun dialogEditCallback(data: ModelBarang)
        fun dialogDeleteCallback(data: ModelBarang)
    }

    override fun onDestroy() {
        super.onDestroy()
        DatabaseHelper.closeDatabase()
    }
}