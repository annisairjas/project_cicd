package com.ewaroenk

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import com.ewaroenk.database.DatabaseHelper
import com.ewaroenk.model.ModelBarang
import kotlinx.android.synthetic.main.activity_tambah_barang.*
import kotlinx.android.synthetic.main.activity_update_barang.*

class UpdateDataBarangActivity : AppCompatActivity() {

    var dataBarang = ModelBarang()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_barang)

        bindView()

        etNamaEdit.addTextChangedListener(Watcher(inNamaEdit))
        etHargaEdit.addTextChangedListener(Watcher(inHargaEdit))
        etStokEdit.addTextChangedListener(Watcher(inStokEdit))

        btnEdit.setOnClickListener {

            val nama = etNamaEdit.text.toString()
            val harga = etHargaEdit.text.toString()
            val stok = etStokEdit.text.toString()

            if (nama.isEmpty()) {
                inNamaEdit.error = "Masukan Nama Barang"
                return@setOnClickListener
            }

            if (harga.isEmpty()) {
                inHargaEdit.error = "Masukan Harga Barang"
                return@setOnClickListener
            }

            if (stok.isEmpty()) {
                inStokEdit.error = "Masukan Stok Barang"
                return@setOnClickListener
            }


            dataBarang.nama = nama
            dataBarang.harga = harga.toInt()
            dataBarang.stok = stok.toInt()

            val stat = DatabaseHelper.updateData(dataBarang)

            if (stat > 0) {
                val bind = Bundle()
                bind.putParcelable("DATA", dataBarang)

                val intent = Intent()
                intent.putExtras(bind)

                setResult(Activity.RESULT_OK, intent)

                Toast.makeText(this, "Berhasil Mengubah Data", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Gagal Mengubah Data", Toast.LENGTH_SHORT).show()
            }
        }
        toolbarEdit.title = "Ubah Data Barang"
    }

    private fun bindView() {
        val bind = intent.extras
        dataBarang = bind.getParcelable("DATA")

        etNamaEdit.setText(dataBarang.nama)
        etHargaEdit.setText(dataBarang.harga.toString())
        etStokEdit.setText(dataBarang.stok.toString())


    }

    private fun getSemester(): List<String> = listOf("SEMESTER", "SEMESTER 1", "SEMESTER 2", "SEMESTER 3", "SEMESTER 4", "SEMESTER 5", "SEMESTER 6", "SEMESTER 7")

    private class Watcher(textinput: TextInputLayout) : TextWatcher {

        val input = textinput

        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            input.isErrorEnabled = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        DatabaseHelper.closeDatabase()
    }
}
