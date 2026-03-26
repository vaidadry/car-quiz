package com.dryzaite.carquiz

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dryzaite.carquiz.audio.SuccessSfxPlayer
import com.dryzaite.carquiz.speech.AndroidBrandSpeaker
import com.dryzaite.carquiz.ui.CarQuizApp
import com.dryzaite.carquiz.ui.theme.CarQuizTheme

class MainActivity : ComponentActivity() {
    private lateinit var brandSpeaker: AndroidBrandSpeaker
    private lateinit var successSfxPlayer: SuccessSfxPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT)
        )

        brandSpeaker = AndroidBrandSpeaker(this)
        successSfxPlayer = SuccessSfxPlayer(this)

        setContent {
            CarQuizTheme {
                CarQuizApp(
                    onSpeakBrand = { brandSpeaker.speak(it) },
                    onPositiveSwipe = {
                        successSfxPlayer.playSuccess()
                    }
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        brandSpeaker.shutdown()
        successSfxPlayer.release()
    }
}
