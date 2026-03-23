package com.dryzaite.carquiz.domain.usecase

import com.dryzaite.carquiz.domain.model.CarBrand
import com.dryzaite.carquiz.domain.repository.CarQuizRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GenerateQuizQuestionUseCaseTest {

    private val repository: CarQuizRepository = mockk()

    @Test
    fun `returns question with expected number of options and answer inside options`() {
        val brands = sampleBrands(5)
        every { repository.getBrands() } returns brands

        val useCase = GenerateQuizQuestionUseCase(repository) { 1 }

        val question = useCase(optionsCount = 3)

        assertEquals(3, question.options.size)
        assertTrue(question.options.any { it.id == question.logoBrand.id })
    }

    @Test(expected = IllegalArgumentException::class)
    fun `throws when there are not enough brands`() {
        every { repository.getBrands() } returns sampleBrands(2)

        val useCase = GenerateQuizQuestionUseCase(repository)

        useCase(optionsCount = 3)
    }

    private fun sampleBrands(size: Int): List<CarBrand> {
        return (1..size).map {
            CarBrand(
                id = "brand-$it",
                name = "Brand $it",
                logoEmoji = "🚗",
            )
        }
    }
}
