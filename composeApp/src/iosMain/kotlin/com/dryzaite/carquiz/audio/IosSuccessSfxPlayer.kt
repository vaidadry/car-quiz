package com.dryzaite.carquiz.audio

import carquiz.composeapp.generated.resources.Res
import kotlinx.cinterop.ExperimentalForeignApi
import org.jetbrains.compose.resources.ExperimentalResourceApi
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSURL

private const val SUCCESS_SOUND_RESOURCE_PATH = "files/badumtz_sound.wav"

@OptIn(ExperimentalResourceApi::class, ExperimentalForeignApi::class)
class IosSuccessSfxPlayer : SuccessSfxPlayer {
    private val player: AVAudioPlayer? = runCatching {
        val resourceUrl = NSURL.URLWithString(Res.getUri(SUCCESS_SOUND_RESOURCE_PATH))
        resourceUrl?.let { url ->
            AVAudioPlayer(contentsOfURL = url, error = null)?.apply {
                prepareToPlay()
            }
        }
    }.getOrNull()

    override fun playSuccess() {
        player?.apply {
            stop()
            currentTime = 0.0
            prepareToPlay()
            play()
        }
    }

    override fun release() {
        player?.stop()
    }
}
