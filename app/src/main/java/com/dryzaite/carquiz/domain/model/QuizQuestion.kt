package com.dryzaite.carquiz.domain.model

data class QuizQuestion(
    val logoBrand: CarBrand,
    val options: List<CarBrand>,
)