package com.dryzaite.carquiz

import android.graphics.Color
import android.media.MediaPlayer
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
    private var badumtzPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT)
        )

        brandSpeaker = AndroidBrandSpeaker(this)
        badumtzPlayer = MediaPlayer.create(this, R.raw.badumtz_sound)

        setContent {
            CarQuizTheme {
                CarQuizApp(
                    onSpeakBrand = { brandSpeaker.speak(it) },
                    onPositiveSwipe = {
                        badumtzPlayer?.let { player ->
                            if (player.isPlaying) player.seekTo(0)
                            player.start()
                        }
                    }
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        brandSpeaker.shutdown()
        badumtzPlayer?.release()
        badumtzPlayer = null
    }
}
