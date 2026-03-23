package com.dryzaite.carquiz.domain.repository

import com.dryzaite.carquiz.domain.model.CarBrand

interface CarQuizRepository {
    fun getBrands(): List<CarBrand>
}