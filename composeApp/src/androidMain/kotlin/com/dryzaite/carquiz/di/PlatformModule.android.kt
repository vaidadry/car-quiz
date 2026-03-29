package com.dryzaite.carquiz.di

import com.dryzaite.carquiz.audio.AndroidSuccessSfxPlayer
import com.dryzaite.carquiz.audio.SuccessSfxPlayer
import com.dryzaite.carquiz.speech.AndroidBrandSpeaker
import com.dryzaite.carquiz.speech.BrandSpeaker
import com.dryzaite.carquiz.stats.data.AndroidStatsStorage
import com.dryzaite.carquiz.stats.data.StatsStorage
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule = module {
    single<BrandSpeaker> { AndroidBrandSpeaker(androidContext()) }
    single<SuccessSfxPlayer> { AndroidSuccessSfxPlayer(androidContext()) }
    single<StatsStorage> { AndroidStatsStorage(androidContext()) }
}
