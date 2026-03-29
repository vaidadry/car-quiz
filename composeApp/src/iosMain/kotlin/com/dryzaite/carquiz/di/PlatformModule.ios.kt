package com.dryzaite.carquiz.di

import com.dryzaite.carquiz.audio.IosSuccessSfxPlayer
import com.dryzaite.carquiz.audio.SuccessSfxPlayer
import com.dryzaite.carquiz.speech.BrandSpeaker
import com.dryzaite.carquiz.speech.IosBrandSpeaker
import com.dryzaite.carquiz.stats.data.IosStatsStorage
import com.dryzaite.carquiz.stats.data.StatsStorage
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<BrandSpeaker> { IosBrandSpeaker() }
    single<SuccessSfxPlayer> { IosSuccessSfxPlayer() }
    single<StatsStorage> { IosStatsStorage() }
}
