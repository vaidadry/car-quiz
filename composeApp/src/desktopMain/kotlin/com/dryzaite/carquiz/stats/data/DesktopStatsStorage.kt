package com.dryzaite.carquiz.stats.data

import java.util.prefs.Preferences

class DesktopStatsStorage : StatsStorage {
    private val prefs = Preferences.userRoot().node("com.dryzaite.carquiz.stats")

    override fun load(): PersistedStats = PersistedStats(
        lastQuizCorrect = prefs.getInt("lastQuizCorrect", 0),
        lastQuizTotal = prefs.getInt("lastQuizTotal", 0),
        lastFlashcardsGuessed = prefs.getInt("lastFlashcardsGuessed", 0),
        lastFlashcardsTotal = prefs.getInt("lastFlashcardsTotal", 0),
        bestQuizCorrect = prefs.getInt("bestQuizCorrect", 0),
        bestQuizTotal = prefs.getInt("bestQuizTotal", 0),
        bestFlashcardsGuessed = prefs.getInt("bestFlashcardsGuessed", 0)
    )

    override fun save(stats: PersistedStats) {
        prefs.putInt("lastQuizCorrect", stats.lastQuizCorrect)
        prefs.putInt("lastQuizTotal", stats.lastQuizTotal)
        prefs.putInt("lastFlashcardsGuessed", stats.lastFlashcardsGuessed)
        prefs.putInt("lastFlashcardsTotal", stats.lastFlashcardsTotal)
        prefs.putInt("bestQuizCorrect", stats.bestQuizCorrect)
        prefs.putInt("bestQuizTotal", stats.bestQuizTotal)
        prefs.putInt("bestFlashcardsGuessed", stats.bestFlashcardsGuessed)
        prefs.flush()
    }
}
