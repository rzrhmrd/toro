package com.rzmmzdh.toro.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Space(val noteItemPadding: Dp = 8.dp, val noteItemsPadding: Dp = 16.dp)

val LocalSpace = compositionLocalOf { Space() }

val MaterialTheme.space: Space
    @Composable
    @ReadOnlyComposable
    get() = LocalSpace.current