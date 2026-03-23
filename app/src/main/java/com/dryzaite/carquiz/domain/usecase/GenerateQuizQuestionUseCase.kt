package com.dryzaite.carquiz.domain.usecase

import com.dryzaite.carquiz.domain.model.CarBrand
import com.dryzaite.carquiz.domain.model.QuizQuestion
import com.dryzaite.carquiz.domain.repository.CarQuizRepository

class GenerateQuizQuestionUseCase(
    private val repository: CarQuizRepository,
    private val randomIndexProvider: (Int) -> Int = { size -> (0 until size).random() },
) {
    operator fun invoke(optionsCount: Int = 3): QuizQuestion {
        val brands = repository.getBrands().distinctBy { it.id }
        require(brands.size >= optionsCount) { "Not enough brands to build a question" }

        val shuffled = brands.shuffled()
        val options = shuffled.take(optionsCount)
        val answer = options[randomIndexProvider(options.size)]

        return QuizQuestion(
            logoBrand = answer,
            options = options,
        )
    }
}