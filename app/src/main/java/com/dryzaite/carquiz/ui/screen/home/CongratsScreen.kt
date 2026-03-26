package com.dryzaite.carquiz.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dryzaite.carquiz.R
import com.dryzaite.carquiz.ui.foundation.OutlinedPillButton
import com.dryzaite.carquiz.ui.theme.AppSurface
import com.dryzaite.carquiz.ui.theme.AppSurfaceSoft
import com.dryzaite.carquiz.ui.theme.BrandPrimary
import com.dryzaite.carquiz.ui.theme.BrandPrimarySoft
import com.dryzaite.carquiz.ui.theme.BrandSecondary

@Composable
fun CongratsScreen(score: Int, total: Int, onPlayAgain: () -> Unit, onHome: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(140.dp)
                .background(BrandPrimarySoft, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Star, contentDescription = null, tint = AppSurface, modifier = Modifier.size(86.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.great_job), style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.height(16.dp))

        Card(shape = RoundedCornerShape(999.dp), colors = CardDefaults.cardColors(containerColor = AppSurfaceSoft)) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Filled.AutoAwesome, contentDescription = null, tint = BrandSecondary)
                Text(
                    text = stringResource(R.string.found_cars_today, score, total),
                    color = BrandSecondary,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Icon(Icons.Filled.DirectionsCar, contentDescription = null, tint = BrandSecondary, modifier = Modifier.size(120.dp))
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedPillButton(stringResource(R.string.play_again), BrandPrimary, Icons.Filled.AutoAwesome, onPlayAgain)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedPillButton(stringResource(R.string.nav_home), BrandSecondary, Icons.Filled.Home, onHome)
        Spacer(modifier = Modifier.height(16.dp))
    }
}
