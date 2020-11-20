package com.ewaroenk

import android.app.Application
import com.jonesrandom.ewaroenk.database.DatabaseHelper


class MyApplication: Application(){

    override fun onCreate() {
        super.onCreate()
        DatabaseHelper.initDatabaseInstance(this)
    }

}
