package com.example.navdrawer

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AppFrisaTheme(content: @Composable () -> Unit) {
    val colors = lightColorScheme(
        primary = Color(0xFF5D1049),
        secondary = Color(0xFFE40046),
        surface = Color.White,
        onSurface = Color.Black,
        background = Color(0xFFFAFAFA)
    )

    MaterialTheme(
        colorScheme = colors,
        shapes = Shapes(),
        content = content
    )
}