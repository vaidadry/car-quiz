package com.dryzaite.carquiz.stats.data

import android.content.Context

private const val PREFS_NAME = "car_quiz_stats"

class AndroidStatsStorage(
    context: Context
) : StatsStorage {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun load(): PersistedStats = PersistedStats(
        lastQuizCorrect = prefs.getInt("lastQuizCorrect", 0),
        lastQuizTotal = prefs.getInt("lastQuizTotal", 0),
        lastFlashcardsGuessed = prefs.getInt("lastFlashcardsGuessed", 0),
        lastFlashcardsTotal = prefs.getInt("lastFlashcardsTotal", 0),
        bestQuizCorrect = prefs.getInt("bestQuizCorrect", 0),
        bestQuizTotal = prefs.getInt("bestQuizTotal", 0),
        bestFlashcardsGuessed = prefs.getInt("bestFlashcardsGuessed", 0),
    )

    override fun save(stats: PersistedStats) {
        prefs.edit()
            .putInt("lastQuizCorrect", stats.lastQuizCorrect)
            .putInt("lastQuizTotal", stats.lastQuizTotal)
            .putInt("lastFlashcardsGuessed", stats.lastFlashcardsGuessed)
            .putInt("lastFlashcardsTotal", stats.lastFlashcardsTotal)
            .putInt("bestQuizCorrect", stats.bestQuizCorrect)
            .putInt("bestQuizTotal", stats.bestQuizTotal)
            .putInt("bestFlashcardsGuessed", stats.bestFlashcardsGuessed)
            .apply()
    }
}
