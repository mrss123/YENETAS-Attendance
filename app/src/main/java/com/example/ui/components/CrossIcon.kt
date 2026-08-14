package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.R

@Composable
fun SimpleCrossIcon(
    modifier: Modifier = Modifier,
    tint: Color? = null,
    size: Dp = 28.dp
) {
    Image(
        painter = painterResource(id = R.drawable.ic_wooden_cross),
        contentDescription = "የመስቀል ዓርማ",
        colorFilter = tint?.let { ColorFilter.tint(it) },
        modifier = modifier.size(size)
    )
}
