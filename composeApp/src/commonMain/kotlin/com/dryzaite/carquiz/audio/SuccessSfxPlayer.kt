package com.dryzaite.carquiz.audio

interface SuccessSfxPlayer {
    fun playSuccess()
    fun release()
}

class SuccessSfxPlayerNoOp : SuccessSfxPlayer {
    override fun playSuccess() = Unit
    override fun release() = Unit
}
