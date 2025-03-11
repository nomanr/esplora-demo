package com.esplora

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class EsploraApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@EsploraApp)
            modules(appModule)
        }
    }
}