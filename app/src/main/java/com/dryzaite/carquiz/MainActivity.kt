package com.dryzaite.carquiz

import android.graphics.Color
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dryzaite.carquiz.speech.AndroidBrandSpeaker
import com.dryzaite.carquiz.ui.CarQuizApp
import com.dryzaite.carquiz.ui.theme.CarQuizTheme

class MainActivity : ComponentActivity() {
    private lateinit var brandSpeaker: AndroidBrandSpeaker
    private var toneGenerator: ToneGenerator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT)
        )

        brandSpeaker = AndroidBrandSpeaker(this)
        toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 90)

        setContent {
            CarQuizTheme {
                CarQuizApp(
                    onSpeakBrand = { brandSpeaker.speak(it) },
                    onPositiveSwipe = { toneGenerator?.startTone(ToneGenerator.TONE_SUP_RADIO_NOTAVAIL, 130) }
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        brandSpeaker.shutdown()
        toneGenerator?.release()
        toneGenerator = null
    }
}