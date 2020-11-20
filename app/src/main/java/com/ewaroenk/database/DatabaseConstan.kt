package com.ewaroenk.database

class DatabaseConstan {

    companion object {
        val DATABASE_NAME = "DB_NAME"
        val DATABASE_VERSION = 1

        val DATABASE_TABEL = "DB_TABEL"
        val ROW_ID = "_id"
        val ROW_NAMA = "nama"
        val ROW_HARGA = "harga"
        val ROW_STOK = "stok"

        val QUERY_CREATE = "CREATE TABLE IF NOT EXISTS $DATABASE_TABEL ($ROW_ID INTEGER PRIMARY KEY AUTOINCREMENT, $ROW_NAMA TEXT , $ROW_HARGA INTEGER , $ROW_STOK INTEGER)"
        val QUERY_UPGRADE = "DROP TABLE IF EXISTS $DATABASE_TABEL"
    }
}