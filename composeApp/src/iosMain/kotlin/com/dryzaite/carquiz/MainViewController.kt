package com.dryzaite.carquiz

import androidx.compose.ui.window.ComposeUIViewController
import com.dryzaite.carquiz.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) {
    com.dryzaite.carquiz.app.App()
}
