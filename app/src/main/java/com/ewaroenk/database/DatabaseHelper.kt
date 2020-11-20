package com.ewaroenk.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.ewaroenk.model.ModelBarang

class DatabaseHelper(ctx: Context) : SQLiteOpenHelper(ctx, DatabaseConstan.DATABASE_NAME, null, DatabaseConstan.DATABASE_VERSION) {

    companion object {
        private lateinit var INSTANCE: DatabaseHelper
        private lateinit var database: SQLiteDatabase
        private var databaseOpen: Boolean = false

        fun closeDatabase() {
            if (database.isOpen && databaseOpen) {
                database.close()
                databaseOpen = false

                Log.i("Database" , "Database close")
            }
        }

        fun initDatabaseInstance(ctx: Context): DatabaseHelper {
            INSTANCE = DatabaseHelper(ctx)
            return INSTANCE
        }

        fun insertData(modelBarang: ModelBarang): Long {

            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database" , "Database Open")
            }

            val values = ContentValues()
            values.put(DatabaseConstan.ROW_NAMA, modelBarang.nama)
            values.put(DatabaseConstan.ROW_HARGA, modelBarang.harga)
            values.put(DatabaseConstan.ROW_STOK, modelBarang.stok)
            return database.insert(DatabaseConstan.DATABASE_TABEL, null, values)
        }

        fun updateData(modelBarang: ModelBarang): Int {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database" , "Database Open")
            }

            val values = ContentValues()
            values.put(DatabaseConstan.ROW_NAMA, modelBarang.nama)
            values.put(DatabaseConstan.ROW_HARGA, modelBarang.harga)
            values.put(DatabaseConstan.ROW_STOK, modelBarang.stok)
            return database.update(DatabaseConstan.DATABASE_TABEL, values, "${DatabaseConstan.ROW_ID} = ${modelBarang.id}", null)
        }

        fun getAllData(): MutableList<ModelBarang> {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database" , "Database Open")
            }

            val data: MutableList<ModelBarang> = ArrayList()
            val cursor = database.rawQuery("SELECT * FROM ${DatabaseConstan.DATABASE_TABEL}", null)
            cursor.use { cur ->
                if (cursor.moveToFirst()) {
                    do {

                        val barang = ModelBarang()
                        barang.id = cur.getInt(cur.getColumnIndex(DatabaseConstan.ROW_ID))
                        barang.nama = cur.getString(cur.getColumnIndex(DatabaseConstan.ROW_NAMA))
                        barang.harga = cur.getInt(cur.getColumnIndex(DatabaseConstan.ROW_HARGA))
                        barang.stok = cur.getInt(cur.getColumnIndex(DatabaseConstan.ROW_STOK))
                        data.add(barang)

                    } while (cursor.moveToNext())
                }
            }
            return data
        }

        fun deleteData(id: Int): Int {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database" , "Database Open")
            }
            return database.delete(DatabaseConstan.DATABASE_TABEL, "${DatabaseConstan.ROW_ID} = $id", null)
        }
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(DatabaseConstan.QUERY_CREATE)
        Log.i("DATABASE", "DATABASE CREATED")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(DatabaseConstan.QUERY_UPGRADE)
        Log.i("DATABASE", "DATABASE UPDATED")
    }

}