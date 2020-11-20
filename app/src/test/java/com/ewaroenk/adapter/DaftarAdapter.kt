package com.ewaroenk.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ewaroenk.R
import com.ewaroenk.model.ModelMahasiswa

class DaftarAdapter(data: MutableList<ModelMahasiswa>, listener: OnItemClickListener) : RecyclerView.Adapter<DaftarHolder>() {

    private val datas = data
    private val listeners = listener

    override fun onBindViewHolder(holder: DaftarHolder?, position: Int) {
        holder?.bind(datas[position], listeners, position)
    }

    override fun getItemCount(): Int = datas.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DaftarHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.row_daftar_barang, parent, false)
        return DaftarHolder(view)
    }
}