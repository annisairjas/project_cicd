package com.ewaroenk

import android.app.Application
import com.ewaroenk.database.DatabaseHelper


class MyApplication: Application(){

    override fun onCreate() {
        super.onCreate()
        DatabaseHelper.initDatabaseInstance(this)
    }

}
