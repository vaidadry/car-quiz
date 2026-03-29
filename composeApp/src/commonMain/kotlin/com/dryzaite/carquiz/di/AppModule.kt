package com.dryzaite.carquiz.di

import com.dryzaite.carquiz.domain.FunFactsUseCase
import com.dryzaite.carquiz.app.AppViewModel
import com.dryzaite.carquiz.feature.home.HomeViewModel
import com.dryzaite.carquiz.feature.learn.LearnViewModel
import com.dryzaite.carquiz.feature.stats.StatsViewModel
import com.dryzaite.carquiz.stats.data.GameStatsRepository
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single { GameStatsRepository(get()) }
    single { FunFactsUseCase() }
    viewModelOf(::AppViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::LearnViewModel)
    viewModelOf(::StatsViewModel)
}
