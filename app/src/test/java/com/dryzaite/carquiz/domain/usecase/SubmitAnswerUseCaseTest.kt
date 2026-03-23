package com.dryzaite.carquiz.domain.usecase

import com.dryzaite.carquiz.domain.model.CarBrand
import com.dryzaite.carquiz.domain.model.QuizQuestion
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SubmitAnswerUseCaseTest {

    private val useCase = SubmitAnswerUseCase()

    @Test
    fun `isAnswerCorrect returns true for matching answer`() {
        val question = sampleQuestion()

        val result = useCase.isAnswerCorrect(question, selectedBrandId = "bmw")

        assertTrue(result)
    }

    @Test
    fun `isAnswerCorrect returns false for wrong answer`() {
        val question = sampleQuestion()

        val result = useCase.isAnswerCorrect(question, selectedBrandId = "audi")

        assertFalse(result)
    }

    @Test
    fun `calculateRoundResult computes percentage and stars`() {
        val result = useCase.calculateRoundResult(totalQuestions = 10, correctAnswers = 7)

        assertEquals(70, result.scorePercentage)
        assertEquals(2, result.stars)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `calculateRoundResult throws when correct answers exceed total`() {
        useCase.calculateRoundResult(totalQuestions = 2, correctAnswers = 3)
    }

    private fun sampleQuestion(): QuizQuestion {
        val bmw = CarBrand(id = "bmw", name = "BMW", logoEmoji = "🔵")
        val audi = CarBrand(id = "audi", name = "Audi", logoEmoji = "⭕")
        val ford = CarBrand(id = "ford", name = "Ford", logoEmoji = "🛻")

        return QuizQuestion(
            logoBrand = bmw,
            options = listOf(bmw, audi, ford),
        )
    }
}
