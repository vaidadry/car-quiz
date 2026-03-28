package com.dryzaite.carquiz.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.media.ToneGenerator
import com.dryzaite.carquiz.R

class AndroidSuccessSfxPlayer(context: Context) : SuccessSfxPlayer {
    private val appContext = context.applicationContext
    private val toneGenerator = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 80)

    private var soundPool: SoundPool? = null
    private var soundId: Int = 0

    @Volatile
    private var isLoaded: Boolean = false

    init {
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(2)
            .setAudioAttributes(attributes)
            .build()
            .apply {
                setOnLoadCompleteListener { _, sampleId, status ->
                    isLoaded = status == 0 && sampleId == soundId
                }
            }

        soundId = soundPool?.load(appContext, R.raw.badumtz_sound, 1) ?: 0
    }

    override fun playSuccess() {
        val pool = soundPool
        if (pool != null && isLoaded && soundId != 0) {
            pool.play(soundId, 1f, 1f, 1, 0, 1f)
        } else {
            toneGenerator.startTone(ToneGenerator.TONE_PROP_ACK, 120)
        }
    }

    override fun release() {
        soundPool?.release()
        soundPool = null
        toneGenerator.release()
    }
}
