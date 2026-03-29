package com.dryzaite.carquiz.core.model

import org.jetbrains.compose.resources.DrawableResource

enum class BrandRegion {
    EU,
    USA,
    JAPAN
}

data class CarBrand(
    val id: String,
    val displayName: String,
    val region: BrandRegion,
    val logo: DrawableResource,
    val accentColorHex: Long
)
