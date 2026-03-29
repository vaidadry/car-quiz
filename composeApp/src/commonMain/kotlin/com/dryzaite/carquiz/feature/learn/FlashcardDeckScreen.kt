package com.dryzaite.carquiz.feature.learn

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import carquiz.composeapp.generated.resources.*
import com.dryzaite.carquiz.core.model.BrandCatalog
import com.dryzaite.carquiz.core.model.CarBrand
import com.dryzaite.carquiz.core.ui.component.BrandLogo
import com.dryzaite.carquiz.core.ui.theme.AppLogoTile
import com.dryzaite.carquiz.core.ui.theme.AppSurface
import com.dryzaite.carquiz.core.ui.theme.AppTextSecondary
import com.dryzaite.carquiz.core.ui.theme.BrandPrimary
import com.dryzaite.carquiz.core.ui.theme.BrandSecondary
import com.dryzaite.carquiz.core.ui.theme.BrandTertiary
import com.dryzaite.carquiz.core.ui.theme.CarQuizTheme
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.sign
import kotlinx.coroutines.launch

@Composable
fun FlashcardDeckScreen(
    brands: List<CarBrand>,
    currentIndex: Int,
    onSwipedRight: () -> Unit,
    onSwipedLeft: () -> Unit
) {
    if (brands.isEmpty()) return

    val xOffset = remember { Animatable(0f) }
    val yOffset = remember { Animatable(0f) }
    val deckSize = remember { androidx.compose.runtime.mutableStateOf(IntSize.Zero) }
    val scope = rememberCoroutineScope()

    val current = brands[currentIndex % brands.size]
    val next = brands[(currentIndex + 1) % brands.size]
    val rotation = (xOffset.value / 36f).coerceIn(-22f, 22f)
    val yayAlpha = (xOffset.value / 220f).coerceIn(0f, 1f)
    val nayAlpha = (-xOffset.value / 220f).coerceIn(0f, 1f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(Res.string.swipe_instruction),
            style = MaterialTheme.typography.headlineSmall,
            color = AppTextSecondary,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(460.dp)
                .onSizeChanged { deckSize.value = it },
            contentAlignment = Alignment.Center
        ) {
            FlashCardContent(
                brand = next,
                modifier = Modifier
                    .fillMaxWidth(0.94f)
                    .wrapContentHeight()
                    .graphicsLayer {
                        scaleX = 0.94f
                        scaleY = 0.94f
                        translationY = 30f
                        alpha = 0.9f
                    }
            )

            FlashCardContent(
                brand = current,
                modifier = Modifier
                    .fillMaxWidth(0.94f)
                    .offset { IntOffset(xOffset.value.roundToInt(), yOffset.value.roundToInt()) }
                    .graphicsLayer { rotationZ = rotation }
                    .pointerInput(currentIndex, deckSize.value) {
                        detectDragGestures(
                            onDrag = { _, dragAmount ->
                                scope.launch {
                                    xOffset.snapTo(xOffset.value + dragAmount.x)
                                    yOffset.snapTo(yOffset.value + dragAmount.y * 0.24f)
                                }
                            },
                            onDragEnd = {
                                scope.launch {
                                    val threshold = deckSize.value.width * 0.25f
                                    if (abs(xOffset.value) > threshold) {
                                        val direction = sign(xOffset.value).takeIf { it != 0f } ?: 1f
                                        xOffset.animateTo(direction * deckSize.value.width * 1.4f, tween(220))
                                        yOffset.animateTo(yOffset.value + 90f, tween(220))
                                        if (direction > 0f) onSwipedRight() else onSwipedLeft()
                                        xOffset.snapTo(0f)
                                        yOffset.snapTo(0f)
                                    } else {
                                        xOffset.animateTo(0f, spring(stiffness = 500f, dampingRatio = 0.75f))
                                        yOffset.animateTo(0f, spring(stiffness = 500f, dampingRatio = 0.75f))
                                    }
                                }
                            }
                        )
                    }
            )

            Text(
                stringResource(Res.string.yay),
                modifier = Modifier.align(Alignment.TopStart).offset(x = 26.dp, y = 34.dp)
                    .graphicsLayer { alpha = yayAlpha },
                color = BrandTertiary,
                fontWeight = FontWeight.Black,
                fontSize = 32.sp
            )
            Text(
                stringResource(Res.string.nay),
                modifier = Modifier.align(Alignment.TopEnd).offset(x = (-26).dp, y = 34.dp)
                    .graphicsLayer { alpha = nayAlpha },
                color = BrandPrimary,
                fontWeight = FontWeight.Black,
                fontSize = 32.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            stringResource(Res.string.card_counter, currentIndex + 1, brands.size),
            color = BrandSecondary,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
private fun FlashCardContent(brand: CarBrand, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(400.dp).padding(24.dp),
        shape = RoundedCornerShape(36.dp),
        colors = CardDefaults.cardColors(containerColor = AppSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.size(170.dp).background(AppLogoTile, RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                BrandLogo(brand = brand, modifier = Modifier.fillMaxSize().padding(16.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                brand.displayName.uppercase(),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Preview
@Composable
private fun FlashcardDeckScreenPreview() {
    CarQuizTheme {
        FlashcardDeckScreen(
            brands = BrandCatalog.allBrands,
            currentIndex = 0,
            onSwipedRight = {},
            onSwipedLeft = {}
        )
    }
}
