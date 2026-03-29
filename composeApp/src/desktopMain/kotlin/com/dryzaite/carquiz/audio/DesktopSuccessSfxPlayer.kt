package com.dryzaite.carquiz.audio

import carquiz.composeapp.generated.resources.Res
import java.net.URI
import javax.sound.sampled.Clip
import javax.sound.sampled.AudioSystem
import org.jetbrains.compose.resources.ExperimentalResourceApi

private const val SUCCESS_SOUND_RESOURCE_PATH = "files/badumtz_sound.wav"

@OptIn(ExperimentalResourceApi::class)
class DesktopSuccessSfxPlayer : SuccessSfxPlayer {
    private val clip: Clip? = runCatching {
        val resourceUrl = URI(Res.getUri(SUCCESS_SOUND_RESOURCE_PATH)).toURL()
        AudioSystem.getAudioInputStream(resourceUrl).use { audioInputStream ->
            AudioSystem.getClip().apply {
                open(audioInputStream)
            }
        }
    }.getOrNull()

    override fun playSuccess() {
        clip?.apply {
            stop()
            framePosition = 0
            start()
        }
    }

    override fun release() {
        clip?.close()
    }
}
