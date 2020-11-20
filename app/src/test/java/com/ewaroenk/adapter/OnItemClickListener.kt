package com.ewaroenk.adapter

import com.ewaroenk.model.ModelMahasiswa

/**
 * Created by jonesrandom on 11/14/17.
 *
 * #JanganLupaBahagia
 *
 */
interface OnItemClickListener {
    fun onClick(data : ModelMahasiswa , position : Int)
}