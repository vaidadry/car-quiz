package com.dryzaite.carquiz.stats.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class PersistedStats(
    val lastQuizCorrect: Int = 0,
    val lastQuizTotal: Int = 0,
    val lastFlashcardsGuessed: Int = 0,
    val lastFlashcardsTotal: Int = 0,
    val bestQuizCorrect: Int = 0,
    val bestQuizTotal: Int = 0,
    val bestFlashcardsGuessed: Int = 0,
    val bestFlashcardsTotal: Int = 0
) {
    val lastQuizAccuracy: Int
        get() = if (lastQuizTotal == 0) 0 else (lastQuizCorrect * 100 / lastQuizTotal)

    val bestQuizAccuracy: Int
        get() = if (bestQuizTotal == 0) 0 else (bestQuizCorrect * 100 / bestQuizTotal)

    val lastFlashcardsAccuracy: Int
        get() = if (lastFlashcardsTotal == 0) 0 else (lastFlashcardsGuessed * 100 / lastFlashcardsTotal)

    val bestFlashcardsAccuracy: Int
        get() = if (bestFlashcardsTotal == 0) 0 else (bestFlashcardsGuessed * 100 / bestFlashcardsTotal)
}

class GameStatsRepository(private val dao: GameStatsDao) {
    val stats: Flow<PersistedStats> = dao.observe().map { it?.toModel() ?: PersistedStats() }

    suspend fun recordQuiz(correct: Int, total: Int) {
        val current = dao.get() ?: GameStatsEntity()
        val improved = isImproved(
            currentCorrect = current.bestQuizCorrect,
            currentTotal = current.bestQuizTotal,
            newCorrect = correct,
            newTotal = total
        )

        dao.upsert(
            current.copy(
                lastQuizCorrect = correct,
                lastQuizTotal = total,
                bestQuizCorrect = if (improved) correct else current.bestQuizCorrect,
                bestQuizTotal = if (improved) total else current.bestQuizTotal
            )
        )
    }

    suspend fun recordFlashcards(rightGuessed: Int, totalSwipes: Int) {
        val current = dao.get() ?: GameStatsEntity()
        dao.upsert(
            current.copy(
                lastFlashcardsGuessed = rightGuessed,
                lastFlashcardsTotal = totalSwipes,
                bestFlashcardsGuessed = maxOf(rightGuessed, current.bestFlashcardsGuessed)
            )
        )
    }

    private fun GameStatsEntity.toModel() = PersistedStats(
        lastQuizCorrect = lastQuizCorrect,
        lastQuizTotal = lastQuizTotal,
        lastFlashcardsGuessed = lastFlashcardsGuessed,
        lastFlashcardsTotal = lastFlashcardsTotal,
        bestQuizCorrect = bestQuizCorrect,
        bestQuizTotal = bestQuizTotal,
        bestFlashcardsGuessed = bestFlashcardsGuessed,
        bestFlashcardsTotal = bestFlashcardsTotal
    )

    private fun isImproved(
        currentCorrect: Int,
        currentTotal: Int,
        newCorrect: Int,
        newTotal: Int
    ): Boolean {
        val currentAcc = if (currentTotal == 0) 0f else currentCorrect * 100f / currentTotal
        val newAcc = if (newTotal == 0) 0f else newCorrect * 100f / newTotal

        return when {
            newAcc > currentAcc -> true
            newAcc < currentAcc -> false
            else -> newCorrect > currentCorrect
        }
    }
}
