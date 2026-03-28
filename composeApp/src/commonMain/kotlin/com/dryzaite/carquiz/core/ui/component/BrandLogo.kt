package com.dryzaite.carquiz.core.ui.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.dryzaite.carquiz.core.model.CarBrand
import org.jetbrains.compose.resources.painterResource

@Composable
fun BrandLogo(brand: CarBrand, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(brand.logo),
        contentDescription = brand.displayName,
        modifier = modifier,
        contentScale = ContentScale.Fit
    )
}
