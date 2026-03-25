package com.dryzaite.carquiz.shared.quiz

import com.dryzaite.carquiz.shared.model.CarBrand
import kotlin.random.Random

data class QuizQuestion(
    val number: Int,
    val total: Int,
    val promptBrand: CarBrand,
    val options: List<CarBrand>,
    val correctBrandId: String
)

class QuizEngine(
    private val brands: List<CarBrand>,
    private val totalQuestions: Int = 10,
    seed: Int? = null
) {
    private val random = if (seed != null) Random(seed) else Random.Default
    private val promptPool = mutableListOf<CarBrand>()

    var correctAnswers: Int = 0
        private set

    var answeredQuestions: Int = 0
        private set

    init {
        require(brands.size >= 4) { "At least 4 brands are required" }
        refillPool()
    }

    fun nextQuestion(): QuizQuestion? {
        if (answeredQuestions >= totalQuestions) return null
        if (promptPool.isEmpty()) refillPool()

        val prompt = promptPool.removeAt(promptPool.lastIndex)
        val distractors = brands
            .asSequence()
            .filterNot { it.id == prompt.id }
            .shuffled(random)
            .take(3)
            .toList()

        val options = (distractors + prompt).shuffled(random)

        return QuizQuestion(
            number = answeredQuestions + 1,
            total = totalQuestions,
            promptBrand = prompt,
            options = options,
            correctBrandId = prompt.id
        )
    }

    fun submitAnswer(question: QuizQuestion, selectedBrandId: String): Boolean {
        check(question.correctBrandId in question.options.map { it.id })
        if (answeredQuestions >= totalQuestions) return false

        answeredQuestions += 1
        val isCorrect = question.correctBrandId == selectedBrandId
        if (isCorrect) correctAnswers += 1
        return isCorrect
    }

    fun reset() {
        correctAnswers = 0
        answeredQuestions = 0
        promptPool.clear()
        refillPool()
    }

    private fun refillPool() {
        promptPool += brands.shuffled(random)
    }
}
