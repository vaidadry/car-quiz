package com.dryzaite.carquiz.di

import androidx.room.Room
import com.dryzaite.carquiz.R
import com.dryzaite.carquiz.audio.SuccessSfxPlayer
import com.dryzaite.carquiz.speech.AndroidBrandSpeaker
import com.dryzaite.carquiz.stats.data.AppDatabase
import com.dryzaite.carquiz.stats.data.GameStatsRepository
import com.dryzaite.carquiz.ui.AppViewModel
import com.dryzaite.carquiz.ui.screen.home.HomeViewModel
import com.dryzaite.carquiz.ui.screen.learn.LearnViewModel
import com.dryzaite.carquiz.ui.screen.stats.StatsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "vroom_explorer.db"
        ).fallbackToDestructiveMigration(dropAllTables = true).build()
    }
    single { get<AppDatabase>().gameStatsDao() }
    single { GameStatsRepository(get()) }
    single(named("carFunFacts")) {
        androidContext().resources.getStringArray(R.array.car_fun_facts).toList()
    }
    factory { AndroidBrandSpeaker(androidContext()) }
    factory { SuccessSfxPlayer(androidContext()) }

    viewModel { AppViewModel() }
    viewModel {
        HomeViewModel(
            brandSpeaker = get(),
            statsRepository = get(),
            funFacts = get(named("carFunFacts"))
        )
    }
    viewModel { LearnViewModel(get(), get()) }
    viewModel { StatsViewModel(get()) }
}
