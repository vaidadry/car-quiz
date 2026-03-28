package com.dryzaite.carquiz.ui.screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dryzaite.carquiz.R
import com.dryzaite.carquiz.shared.model.BrandCatalog
import com.dryzaite.carquiz.shared.quiz.QuizEngine
import com.dryzaite.carquiz.shared.quiz.QuizQuestion
import com.dryzaite.carquiz.ui.foundation.BrandLogo
import com.dryzaite.carquiz.ui.theme.AppBorderNeutral
import com.dryzaite.carquiz.ui.theme.AppError
import com.dryzaite.carquiz.ui.theme.AppLogoTile
import com.dryzaite.carquiz.ui.theme.AppSuccess
import com.dryzaite.carquiz.ui.theme.AppSurface
import com.dryzaite.carquiz.ui.theme.AppTextPrimary
import com.dryzaite.carquiz.ui.theme.BrandPrimary
import com.dryzaite.carquiz.ui.theme.BrandSecondary
import com.dryzaite.carquiz.ui.theme.BrandSecondarySoft
import com.dryzaite.carquiz.ui.theme.BrandTertiary
import com.dryzaite.carquiz.ui.theme.CarQuizTheme
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun LogoQuizScreen(
    question: QuizQuestion,
    selectedAnswerId: String?,
    onReplayPrompt: () -> Unit,
    onAnswerSelected: (String) -> Unit
) {
    val progress = question.number.toFloat() / question.total

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(BrandSecondarySoft, CircleShape)
                .align(Alignment.CenterHorizontally)
                .pointerInput(question.number) {
                    detectDragGestures(
                        onDragStart = { onReplayPrompt() },
                        onDrag = { _, _ -> }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.AutoMirrored.Filled.VolumeUp,
                contentDescription = stringResource(R.string.speak_brand),
                tint = BrandSecondary,
                modifier = Modifier.size(36.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            stringResource(R.string.which_one_is_the),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            stringResource(
                R.string.brand_with_question,
                question.promptBrand.displayName.uppercase(Locale.getDefault())
            ),
            style = MaterialTheme.typography.headlineLarge,
            color = BrandPrimary,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        question.options.chunked(2).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                row.forEach { brand ->
                    val correct = brand.id == question.correctBrandId
                    val selected = selectedAnswerId == brand.id
                    val border = when {
                        selected && correct -> AppSuccess
                        selected && !correct -> AppError
                        else -> AppBorderNeutral
                    }
                    Card(
                        modifier = Modifier.weight(1f).height(170.dp),
                        shape = RoundedCornerShape(36.dp),
                        colors = CardDefaults.cardColors(containerColor = AppSurface),
                        border = BorderStroke(3.dp, border),
                        onClick = {
                            if (selectedAnswerId != null) return@Card
                            onAnswerSelected(brand.id)
                        }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier.size(64.dp).background(AppLogoTile, RoundedCornerShape(2.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                BrandLogo(brand = brand, modifier = Modifier.fillMaxSize().padding(8.dp))
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = brand.displayName.uppercase(Locale.getDefault()),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = AppTextPrimary,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                if (row.size == 1) Spacer(modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.question_counter, question.number, question.total),
                color = BrandSecondary,
                fontWeight = FontWeight.Bold
            )
            Icon(Icons.Filled.AutoAwesome, contentDescription = null, tint = BrandTertiary)
        }

        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .background(BrandSecondarySoft, RoundedCornerShape(999.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .height(16.dp)
                    .background(BrandSecondary, RoundedCornerShape(999.dp))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LogoQuizScreenPreview() {
    val previewQuestion = QuizEngine(BrandCatalog.allBrands, totalQuestions = 10, seed = 1)
        .nextQuestion() ?: return

    CarQuizTheme {
        LogoQuizScreen(
            question = previewQuestion,
            selectedAnswerId = null,
            onReplayPrompt = {},
            onAnswerSelected = {}
        )
    }
}
