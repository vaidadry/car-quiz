package com.dryzaite.carquiz.ui.foundation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dryzaite.carquiz.R
import com.dryzaite.carquiz.ui.MainTab
import com.dryzaite.carquiz.ui.theme.AppSurfaceTint
import com.dryzaite.carquiz.ui.theme.BrandPrimary
import com.dryzaite.carquiz.ui.theme.BrandSecondary

@Composable
fun BottomNav(tab: MainTab, onTabChange: (MainTab) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(34.dp),
        color = androidx.compose.ui.graphics.Color.Transparent,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItem(MainTab.HOME, tab == MainTab.HOME, onTabChange)
            NavItem(MainTab.LEARN, tab == MainTab.LEARN, onTabChange)
            NavItem(MainTab.STATS, tab == MainTab.STATS, onTabChange)
        }
    }
}

@Composable
private fun NavItem(item: MainTab, selected: Boolean, onTabChange: (MainTab) -> Unit) {
    val color = if (selected) BrandPrimary else BrandSecondary

    Surface(shape = RoundedCornerShape(999.dp), color = AppSurfaceTint, onClick = { onTabChange(item) }) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = when (item) {
                    MainTab.HOME -> Icons.Filled.Home
                    MainTab.LEARN -> Icons.Filled.Style
                    MainTab.STATS -> Icons.Filled.BarChart
                },
                contentDescription = null,
                tint = color
            )
            Text(
                text = when (item) {
                    MainTab.HOME -> stringResource(R.string.nav_home)
                    MainTab.LEARN -> stringResource(R.string.nav_learn)
                    MainTab.STATS -> stringResource(R.string.nav_stats)
                },
                color = color,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
