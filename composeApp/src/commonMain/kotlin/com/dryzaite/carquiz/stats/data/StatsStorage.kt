package com.dryzaite.carquiz.stats.data

interface StatsStorage {
    fun load(): PersistedStats
    fun save(stats: PersistedStats)
}
