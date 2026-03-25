package com.dryzaite.carquiz.shared.model

enum class BrandRegion {
    EU,
    USA,
    JAPAN
}

data class CarBrand(
    val id: String,
    val displayName: String,
    val region: BrandRegion,
    val initials: String,
    val accentColorHex: Long
)
