package com.dryzaite.carquiz.speech

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class AndroidBrandSpeaker(context: Context) {
    private var textToSpeech: TextToSpeech? = null

    init {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech?.language = Locale.US
                textToSpeech?.setSpeechRate(0.9f)
            }
        }
    }

    fun speak(brandName: String) {
        textToSpeech?.speak(brandName, TextToSpeech.QUEUE_FLUSH, null, "brand-$brandName")
    }

    fun shutdown() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        textToSpeech = null
    }
}
