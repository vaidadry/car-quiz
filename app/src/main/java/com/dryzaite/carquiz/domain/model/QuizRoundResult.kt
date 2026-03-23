package com.dryzaite.carquiz.domain.model

data class QuizRoundResult(
    val totalQuestions: Int,
    val correctAnswers: Int,
) {
    val scorePercentage: Int = if (totalQuestions == 0) 0 else (correctAnswers * 100) / totalQuestions
    val stars: Int = when {
        totalQuestions == 0 -> 0
        scorePercentage >= 90 -> 3
        scorePercentage >= 60 -> 2
        scorePercentage >= 30 -> 1
        else -> 0
    }
}
