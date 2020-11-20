package com.ewaroenk.adapter

import com.ewaroenk.model.ModelBarang

interface OnItemClickListener {
    fun onClick(data : ModelBarang , position : Int)
}