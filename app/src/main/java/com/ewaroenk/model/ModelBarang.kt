package com.ewaroenk.model


import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@SuppressLint("ParcelCreator")
@Parcelize
data class ModelBarang(var id: Int, var nama: String, var harga: Int, var stok: Int) : Parcelable {
    constructor() : this(0, "", 0, 0)

}