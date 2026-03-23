package com.dryzaite.carquiz.domain.usecase

import com.dryzaite.carquiz.domain.model.QuizQuestion
import com.dryzaite.carquiz.domain.model.QuizRoundResult

class SubmitAnswerUseCase {
    fun isAnswerCorrect(question: QuizQuestion, selectedBrandId: String): Boolean {
        return question.logoBrand.id == selectedBrandId
    }

    fun calculateRoundResult(totalQuestions: Int, correctAnswers: Int): QuizRoundResult {
        require(totalQuestions >= 0) { "totalQuestions must be >= 0" }
        require(correctAnswers >= 0) { "correctAnswers must be >= 0" }
        require(correctAnswers <= totalQuestions) { "correctAnswers cannot exceed totalQuestions" }

        return QuizRoundResult(
            totalQuestions = totalQuestions,
            correctAnswers = correctAnswers,
        )
    }
}