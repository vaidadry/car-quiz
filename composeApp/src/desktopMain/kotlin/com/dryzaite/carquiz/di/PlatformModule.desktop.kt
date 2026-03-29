package com.dryzaite.carquiz.di

import com.dryzaite.carquiz.audio.DesktopSuccessSfxPlayer
import com.dryzaite.carquiz.audio.SuccessSfxPlayer
import com.dryzaite.carquiz.speech.BrandSpeaker
import com.dryzaite.carquiz.speech.DesktopBrandSpeaker
import com.dryzaite.carquiz.stats.data.DesktopStatsStorage
import com.dryzaite.carquiz.stats.data.StatsStorage
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<BrandSpeaker> { DesktopBrandSpeaker() }
    single<SuccessSfxPlayer> { DesktopSuccessSfxPlayer() }
    single<StatsStorage> { DesktopStatsStorage() }
}
