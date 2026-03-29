package com.dryzaite.carquiz.speech

import platform.AVFAudio.AVSpeechBoundary
import platform.AVFAudio.AVSpeechSynthesisVoice
import platform.AVFAudio.AVSpeechSynthesizer
import platform.AVFAudio.AVSpeechUtterance

class IosBrandSpeaker : BrandSpeaker {
    private val synthesizer = AVSpeechSynthesizer()

    override fun speak(brandName: String) {
        if (synthesizer.speaking) {
            synthesizer.stopSpeakingAtBoundary(AVSpeechBoundary.AVSpeechBoundaryImmediate)
        }
        val utterance = AVSpeechUtterance(string = brandName)
        utterance.rate = 0.42f
        utterance.voice = AVSpeechSynthesisVoice.voiceWithLanguage("en-US")
        synthesizer.speakUtterance(utterance)
    }

    override fun shutdown() {
        if (synthesizer.speaking) {
            synthesizer.stopSpeakingAtBoundary(AVSpeechBoundary.AVSpeechBoundaryImmediate)
        }
    }
}
