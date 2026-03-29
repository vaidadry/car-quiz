package com.dryzaite.carquiz.stats.data

import platform.Foundation.NSUserDefaults

class IosStatsStorage : StatsStorage {
    private val defaults = NSUserDefaults.standardUserDefaults

    override fun load(): PersistedStats = PersistedStats(
        lastQuizCorrect = defaults.integerForKey("lastQuizCorrect").toInt(),
        lastQuizTotal = defaults.integerForKey("lastQuizTotal").toInt(),
        lastFlashcardsGuessed = defaults.integerForKey("lastFlashcardsGuessed").toInt(),
        lastFlashcardsTotal = defaults.integerForKey("lastFlashcardsTotal").toInt(),
        bestQuizCorrect = defaults.integerForKey("bestQuizCorrect").toInt(),
        bestQuizTotal = defaults.integerForKey("bestQuizTotal").toInt(),
        bestFlashcardsGuessed = defaults.integerForKey("bestFlashcardsGuessed").toInt(),
    )

    override fun save(stats: PersistedStats) {
        defaults.setInteger(stats.lastQuizCorrect.toLong(), forKey = "lastQuizCorrect")
        defaults.setInteger(stats.lastQuizTotal.toLong(), forKey = "lastQuizTotal")
        defaults.setInteger(stats.lastFlashcardsGuessed.toLong(), forKey = "lastFlashcardsGuessed")
        defaults.setInteger(stats.lastFlashcardsTotal.toLong(), forKey = "lastFlashcardsTotal")
        defaults.setInteger(stats.bestQuizCorrect.toLong(), forKey = "bestQuizCorrect")
        defaults.setInteger(stats.bestQuizTotal.toLong(), forKey = "bestQuizTotal")
        defaults.setInteger(stats.bestFlashcardsGuessed.toLong(), forKey = "bestFlashcardsGuessed")
    }
}
