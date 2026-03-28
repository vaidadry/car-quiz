package com.dryzaite.carquiz.feature.stats

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import carquiz.composeapp.generated.resources.*
import com.dryzaite.carquiz.stats.data.PersistedStats
import com.dryzaite.carquiz.core.ui.theme.AppSurface
import com.dryzaite.carquiz.core.ui.theme.AppSurfaceSoft
import com.dryzaite.carquiz.core.ui.theme.AppTextPrimary
import com.dryzaite.carquiz.core.ui.theme.AppTextSecondary
import com.dryzaite.carquiz.core.ui.theme.BrandPrimary
import com.dryzaite.carquiz.core.ui.theme.BrandTertiary
import com.dryzaite.carquiz.core.ui.theme.CarQuizTheme

@Composable
fun StatsScreen(stats: PersistedStats) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text(
            text = stringResource(Res.string.your_stats),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = AppTextPrimary
        )

        StatsGroupCard(
            borderColor = BrandPrimary,
            topTitle =  stringResource(Res.string.quiz_results),
            topValue = "${stats.lastQuizCorrect}/${stats.lastQuizTotal}",
            topSubtitle = stringResource(if (stats.lastQuizTotal == 0) Res.string.quiz_no_games else Res.string.great_job),
            topPercent = "${stats.lastQuizAccuracy}%",
            bottomTitle = stringResource(Res.string.flashcards_label),
            bottomValue = "${stats.lastFlashcardsGuessed}/${stats.lastFlashcardsTotal}",
            bottomSubtitle = stringResource(Res.string.flashcards_guessed),
            bottomPercent = "${stats.lastFlashcardsAccuracy}%"
        )

        Text(
            text = stringResource(Res.string.results_all_time_best),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = AppTextPrimary
        )

        StatsGroupCard(
            borderColor = BrandTertiary,
            topTitle = stringResource(Res.string.results_top_score),
            topValue = "${stats.bestQuizCorrect}/${stats.bestQuizTotal}",
            topSubtitle = stringResource(if (stats.bestQuizTotal == 0) Res.string.quiz_no_games else Res.string.quiz_master),
            topPercent = "${stats.bestQuizAccuracy}%",
            bottomTitle = stringResource(Res.string.results_max_guessed),
            bottomValue = "${stats.bestFlashcardsGuessed}/${stats.bestFlashcardsTotal}",
            bottomSubtitle = stringResource(Res.string.results_in_one_go),
            bottomPercent = "${stats.bestFlashcardsAccuracy}%"
        )
    }
}

@Composable
private fun StatsGroupCard(
    borderColor: Color,
    topTitle: String,
    topValue: String,
    topSubtitle: String,
    topPercent: String,
    bottomTitle: String,
    bottomValue: String,
    bottomSubtitle: String,
    bottomPercent: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(36.dp),
        colors = CardDefaults.cardColors(containerColor = AppSurface),
        border = BorderStroke(4.dp, borderColor)
    ) {
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(18.dp)) {
            StatsRow(topTitle, topValue, topSubtitle, topPercent, borderColor)
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(AppSurfaceSoft)
            )
            StatsRow(bottomTitle, bottomValue, bottomSubtitle, bottomPercent, borderColor)
        }
    }
}

@Composable
private fun StatsRow(
    title: String,
    value: String,
    subtitle: String,
    percent: String,
    percentColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, lineHeight = 30.sp)
            Text(subtitle, style = MaterialTheme.typography.titleMedium, color = AppTextSecondary)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(value, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.ExtraBold)
            Text(percent, style = MaterialTheme.typography.titleLarge, color = percentColor, fontWeight = FontWeight.ExtraBold)
        }
    }
}
@Preview
@Composable
private fun StatsScreenPreview() {
    CarQuizTheme {
        StatsScreen(PersistedStats())
    }
}
