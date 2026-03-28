package com.dryzaite.carquiz.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dryzaite.carquiz.core.ui.theme.AppSurfaceTint
import com.dryzaite.carquiz.core.ui.theme.BrandPrimary
import com.dryzaite.carquiz.core.ui.theme.BrandSecondary

data class BottomNavTab(
    val icon: ImageVector,
    val label: String,
    val isSelected: Boolean,
    val onClick: () -> Unit
)

@Composable
fun BottomNav(tabs: List<BottomNavTab>) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.Transparent,
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
            tabs.forEach { tab ->
                NavItem(tab = tab)
            }
        }
    }
}

@Composable
private fun NavItem(tab: BottomNavTab) {
    val color: Color = if (tab.isSelected) BrandPrimary else BrandSecondary

    Surface(shape = RoundedCornerShape(999.dp), color = AppSurfaceTint, onClick = tab.onClick) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = tab.icon,
                contentDescription = null,
                tint = color
            )
            Text(
                text = tab.label,
                color = color,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
