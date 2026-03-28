package com.dryzaite.carquiz.speech

class DesktopBrandSpeaker : BrandSpeaker {
    private var currentProcess: Process? = null

    override fun speak(brandName: String) {
        currentProcess?.destroy()
        currentProcess = runCatching {
            ProcessBuilder("say", brandName).start()
        }.getOrNull()
    }

    override fun shutdown() {
        currentProcess?.destroy()
        currentProcess = null
    }
}
