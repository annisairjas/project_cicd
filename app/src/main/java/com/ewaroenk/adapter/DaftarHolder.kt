package com.ewaroenk.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.ewaroenk.model.ModelBarang
import kotlinx.android.synthetic.main.row_daftar_barang.view.*

class DaftarHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    fun bind(data: ModelBarang, listener: OnItemClickListener, position: Int) = with(itemView) {

        rowAv.text = data.nama.substring(0, 1).capitalize()
        rowNama.text = data.nama
        rowHarga.text = data.harga.toString()
        rowStok.text = data.stok.toString()

        setOnClickListener { listener.onClick(data, position) }

    }
}