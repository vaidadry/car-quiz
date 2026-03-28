package com.dryzaite.carquiz.speech

interface BrandSpeaker {
    fun speak(brandName: String)
    fun shutdown()
}

class NoOpBrandSpeaker : BrandSpeaker {
    override fun speak(brandName: String) = Unit
    override fun shutdown() = Unit
}
