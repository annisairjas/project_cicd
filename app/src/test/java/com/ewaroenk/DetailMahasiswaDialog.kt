package com.ewaroenk

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jonesrandom.ewaroenk.database.DatabaseHelper
import com.jonesrandom.ewaroenk.model.ModelMahasiswa
import kotlinx.android.synthetic.main.dialog_detail_barang.*


class DetailMahasiswaDialog : BottomSheetDialogFragment() {

    private var dataMahasiswa = ModelMahasiswa()

    companion object {
        lateinit private var listeners: OnDialogItemClick

        fun newInstance(data: ModelMahasiswa, listener: OnDialogItemClick): DetailBarangDialog {

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
            dataMahasiswa = args.getParcelable("DATA")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_detail_barang, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialogNama.text = dataMahasiswa.nama.toUpperCase()
        dialogNim.text = dataMahasiswa.nim.toString()
        dialogSemester.text = dataMahasiswa.semster

        toolbarDialog.inflateMenu(R.menu.dialog_menu)
        toolbarDialog.setOnMenuItemClickListener {

            when (it.itemId) {

                R.id.dialogEdit -> {
                    listeners.dialogEditCallback(dataMahasiswa)
                    dialog.dismiss()
                }
                R.id.dialogHapus -> {
                    val build = context?.let { it1 -> AlertDialog.Builder(it1) }
                    build?.setTitle("Hapus Data")
                    build?.setMessage("Apakah Kamu Ingin Menghapus Data ${dataMahasiswa.nama.toUpperCase()}")
                    build?.setPositiveButton("HAPUS", { _, _ ->

                        val stas = DatabaseHelper.deleteData(dataMahasiswa.id)

                        if (stas != 0) {
                            dialog.dismiss()
                            listeners.dialogDeleteCallback(dataMahasiswa)

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
        fun dialogEditCallback(data: ModelMahasiswa)
        fun dialogDeleteCallback(data: ModelMahasiswa)
    }

    override fun onDestroy() {
        super.onDestroy()
        DatabaseHelper.closeDatabase()
    }
}