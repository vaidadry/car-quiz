package com.dryzaite.carquiz.ui.foundation

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.dryzaite.carquiz.shared.model.CarBrand

@Composable
fun BrandLogo(brand: CarBrand, modifier: Modifier = Modifier) {
    require(brand.logo != 0) { "Missing logo id for brand: ${brand.id}" }
    Image(
        painter = painterResource(id = brand.logo),
        contentDescription = brand.displayName,
        modifier = modifier,
        contentScale = ContentScale.Fit
    )
}
