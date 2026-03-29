package com.dryzaite.carquiz.feature.home

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.StringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import carquiz.composeapp.generated.resources.*
import com.dryzaite.carquiz.core.ui.component.OutlinedPillButton
import com.dryzaite.carquiz.core.ui.theme.CarQuizTheme
import com.dryzaite.carquiz.core.ui.theme.AppSurface
import com.dryzaite.carquiz.core.ui.theme.AppTextPrimary
import com.dryzaite.carquiz.core.ui.theme.AppTextSecondary
import com.dryzaite.carquiz.core.ui.theme.BrandPrimary
import com.dryzaite.carquiz.core.ui.theme.BrandTertiary
import com.dryzaite.carquiz.core.ui.theme.HomeFactCard
import com.dryzaite.carquiz.core.ui.theme.HomeFactIconBg

@Composable
fun WelcomeScreen(
    funFact: StringResource?,
    onStart: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(40.dp),
            colors = CardDefaults.cardColors(containerColor = AppSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.DirectionsCar,
                    contentDescription = null,
                    tint = BrandPrimary,
                    modifier = Modifier.size(132.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(stringResource(Res.string.welcome_title), style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.ExtraBold, color = AppTextPrimary)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(Res.string.welcome_subtitle),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    color = AppTextSecondary
                )

                Spacer(modifier = Modifier.height(16.dp))
                OutlinedPillButton(
                    label = stringResource(Res.string.play),
                    textColor = BrandPrimary,
                    icon = Icons.Filled.VideogameAsset,
                    onClick = onStart
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = HomeFactCard)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(HomeFactIconBg, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Lightbulb, contentDescription = null, tint = AppSurface)
                }
                Column {
                    Text(
                        stringResource(Res.string.did_you_know),
                        color = HomeFactIconBg,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        funFact?.let { stringResource(it) }.orEmpty(),
                        color = BrandTertiary,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun WelcomeScreenPreview() {
    CarQuizTheme {
        WelcomeScreen(funFact = Res.string.fun_fact_1, onStart = {})
    }
}
