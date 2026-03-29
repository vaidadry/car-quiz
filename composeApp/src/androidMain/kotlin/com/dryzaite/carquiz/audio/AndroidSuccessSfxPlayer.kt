package com.dryzaite.carquiz.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import com.dryzaite.carquiz.R

class AndroidSuccessSfxPlayer(context: Context) : SuccessSfxPlayer {
    private val appContext = context.applicationContext
    private val mediaPlayer: MediaPlayer? = MediaPlayer.create(appContext, R.raw.badumtz_sound)?.apply {
        setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        )
        isLooping = false
    }

    override fun playSuccess() {
        mediaPlayer?.apply {
            if (isPlaying) {
                seekTo(0)
            } else {
                seekTo(0)
                start()
            }
        }
    }

    override fun release() {
        mediaPlayer?.release()
    }
}
