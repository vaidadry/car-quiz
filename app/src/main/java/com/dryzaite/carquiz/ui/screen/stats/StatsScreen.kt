package com.dryzaite.carquiz.ui.screen.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dryzaite.carquiz.R
import com.dryzaite.carquiz.ui.theme.AppSurface
import com.dryzaite.carquiz.ui.theme.AppTextSecondary
import com.dryzaite.carquiz.ui.theme.BrandSecondary
import com.dryzaite.carquiz.ui.theme.BrandTertiary
import com.dryzaite.carquiz.ui.theme.CarQuizTheme

@Composable
fun StatsScreen(
    correct: Int,
    answered: Int,
    lastScore: Int,
    lastTotal: Int,
    rightSwipes: Int,
    totalSwipes: Int
) {
    val quizPct = if (answered == 0) 0 else (correct * 100 / answered)
    val swipePct = if (totalSwipes == 0) 0 else (rightSwipes * 100 / totalSwipes)

    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(36.dp), colors = CardDefaults.cardColors(containerColor = AppSurface)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(stringResource(R.string.your_stats), style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.ExtraBold)
            Text(stringResource(R.string.quiz_answers, answered), style = MaterialTheme.typography.titleLarge)
            Text(stringResource(R.string.quiz_correct, correct), style = MaterialTheme.typography.titleLarge)
            Text(stringResource(R.string.quiz_accuracy, quizPct), style = MaterialTheme.typography.titleLarge, color = BrandSecondary, fontWeight = FontWeight.ExtraBold)
            Text(stringResource(R.string.last_quiz, lastScore, lastTotal), style = MaterialTheme.typography.titleMedium, color = AppTextSecondary)
            Spacer(modifier = Modifier.height(16.dp))
            Text(stringResource(R.string.flashcards_swiped, totalSwipes), style = MaterialTheme.typography.titleLarge)
            Text(stringResource(R.string.right_swipes, rightSwipes), style = MaterialTheme.typography.titleLarge)
            Text(stringResource(R.string.yay_rate, swipePct), style = MaterialTheme.typography.titleLarge, color = BrandTertiary, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Preview
@Composable
private fun StatsScreenPreview() {
    CarQuizTheme {
        StatsScreen(correct = 15, answered = 20, lastScore = 8, lastTotal = 10, rightSwipes = 12, totalSwipes = 16)
    }
}
