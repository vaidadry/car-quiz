package com.dryzaite.carquiz

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.room.Room
import com.dryzaite.carquiz.audio.SuccessSfxPlayer
import com.dryzaite.carquiz.speech.AndroidBrandSpeaker
import com.dryzaite.carquiz.stats.data.AppDatabase
import com.dryzaite.carquiz.stats.data.GameStatsRepository
import com.dryzaite.carquiz.ui.CarQuizApp
import com.dryzaite.carquiz.ui.theme.CarQuizTheme

class MainActivity : ComponentActivity() {
    private lateinit var brandSpeaker: AndroidBrandSpeaker
    private lateinit var successSfxPlayer: SuccessSfxPlayer
    private lateinit var gameStatsRepository: GameStatsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT)
        )

        brandSpeaker = AndroidBrandSpeaker(this)
        successSfxPlayer = SuccessSfxPlayer(this)
        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "vroom_explorer.db"
        ).fallbackToDestructiveMigration(dropAllTables = true).build()
        gameStatsRepository = GameStatsRepository(database.gameStatsDao())

        setContent {
            CarQuizTheme {
                CarQuizApp(
                    statsRepository = gameStatsRepository,
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
