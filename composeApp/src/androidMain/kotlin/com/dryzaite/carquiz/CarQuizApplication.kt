package com.dryzaite.carquiz

import android.app.Application
import com.dryzaite.carquiz.di.initKoin
import org.koin.android.ext.koin.androidContext

class CarQuizApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@CarQuizApplication)
        }
    }
}
