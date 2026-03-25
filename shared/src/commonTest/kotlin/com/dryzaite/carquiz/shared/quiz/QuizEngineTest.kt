package com.dryzaite.carquiz.shared.quiz

import com.dryzaite.carquiz.shared.model.BrandCatalog
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class QuizEngineTest {
    @Test
    fun generatesQuestionWithFourOptionsAndCorrectBrandIncluded() {
        val engine = QuizEngine(BrandCatalog.allBrands, totalQuestions = 1, seed = 42)

        val question = engine.nextQuestion()

        assertNotNull(question)
        assertEquals(4, question.options.size)
        assertTrue(question.options.any { it.id == question.correctBrandId })
    }

    @Test
    fun tracksScoreAcrossSubmissions() {
        val engine = QuizEngine(BrandCatalog.allBrands, totalQuestions = 2, seed = 7)

        val questionOne = engine.nextQuestion()
        assertNotNull(questionOne)
        engine.submitAnswer(questionOne, questionOne.correctBrandId)

        val questionTwo = engine.nextQuestion()
        assertNotNull(questionTwo)
        val wrongId = questionTwo.options.first { it.id != questionTwo.correctBrandId }.id
        engine.submitAnswer(questionTwo, wrongId)

        assertEquals(2, engine.answeredQuestions)
        assertEquals(1, engine.correctAnswers)
    }
}
