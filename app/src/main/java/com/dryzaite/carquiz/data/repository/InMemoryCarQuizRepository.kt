package com.dryzaite.carquiz.data.repository

import com.dryzaite.carquiz.domain.model.CarBrand
import com.dryzaite.carquiz.domain.repository.CarQuizRepository

class InMemoryCarQuizRepository : CarQuizRepository {
    override fun getBrands(): List<CarBrand> = seedBrands

    private val seedBrands = listOf(
        CarBrand(id = "toyota", name = "Toyota", logoEmoji = "🚗"),
        CarBrand(id = "bmw", name = "BMW", logoEmoji = "🔵"),
        CarBrand(id = "audi", name = "Audi", logoEmoji = "⭕"),
        CarBrand(id = "ford", name = "Ford", logoEmoji = "🛻"),
        CarBrand(id = "honda", name = "Honda", logoEmoji = "🏎️"),
        CarBrand(id = "tesla", name = "Tesla", logoEmoji = "⚡"),
        CarBrand(id = "volkswagen", name = "Volkswagen", logoEmoji = "🌀"),
        CarBrand(id = "mercedes", name = "Mercedes", logoEmoji = "⭐"),
    )
}