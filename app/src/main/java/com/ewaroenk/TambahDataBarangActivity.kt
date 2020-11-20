package com.ewaroenk

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

class TambahDataBarangActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_barang)

        toolbar.title = "EWaroenk"

        etNama.addTextChangedListener(Watcher(inNama))
        etHarga.addTextChangedListener(Watcher(inHarga))
        etStok.addTextChangedListener(Watcher(inStok))

        btnInsert.setOnClickListener {

            val nama = etNama.text.toString()
            val harga = etHarga.text.toString()
            val stok = etStok.text.toString()

            if (nama.isEmpty()) {
                inNama.error = "Masukan Nama Barang"
                return@setOnClickListener
            }

            if (harga.isEmpty()) {
                inHarga.error = "Masukan Harga Barang"
                return@setOnClickListener
            }

            if (stok.isEmpty()) {
                inStok.error = "Masukan Stok Barang"
                return@setOnClickListener
            }


            val data = ModelBarang()
            data.nama = nama
            data.harga = harga.toInt()
            data.stok = stok.toInt()

            val stat = DatabaseHelper.insertData(data)

            if (stat > 0) {

                etNama.text.clear()
                etHarga.text.clear()
                etStok.text.clear()

                etHarga.clearFocus()
                etNama.clearFocus()
                etStok.clearFocus()

                Toast.makeText(this, "Berhasil Menambah Data", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Gagal Menambah Data", Toast.LENGTH_SHORT).show()
            }

        }
        btnLihatData.setOnClickListener {
            startActivity(Intent(this, DaftarBarangActivity::class.java))
        }

    }

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
