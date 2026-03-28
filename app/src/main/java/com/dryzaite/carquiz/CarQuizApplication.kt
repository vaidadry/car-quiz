package com.dryzaite.carquiz

import android.app.Application
import com.dryzaite.carquiz.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CarQuizApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CarQuizApplication)
            modules(appModule)
        }
    }
}
