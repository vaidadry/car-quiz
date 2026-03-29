package com.dryzaite.carquiz.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module

private var koinStarted = false

fun initKoin(
    appDeclaration: org.koin.core.KoinApplication.() -> Unit = {}
) {
    if (koinStarted) return

    startKoin {
        appDeclaration()
        modules(appModule, platformModule)
    }
    koinStarted = true
}

expect val platformModule: Module
