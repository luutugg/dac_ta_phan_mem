package com.example.dactaphanmem.presentation

import android.app.Application
import com.example.dactaphanmem.data.database.AppDatabase

private var application: Application? = null

private fun setApplication(context: Application) {
    application = context
}

fun getApplication() = application ?: throw RuntimeException("Application context mustn't null")

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setApplication(this)
        AppDatabase.createDatabase(this)
        AppPreferences.init(this)
    }
}


