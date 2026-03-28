package com.dryzaite.carquiz

import carquiz.composeapp.generated.resources.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import carquiz.composeapp.generated.resources.Res
import com.dryzaite.carquiz.di.initKoin
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        initKoin()
        val appName = runBlocking { getString(Res.string.app_name) }
        application {
            Window(
                onCloseRequest = ::exitApplication,
                title = appName,
                icon = painterResource(Res.drawable.app_icon)
            ) {
                com.dryzaite.carquiz.app.App()
            }
        }
    }
}
