package com.rzmmzdh.toro.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Size(
    val noteCard: Dp = 192.dp,
    val noteTitleInputHeight: Dp = 84.dp,
    val noteTitleInputPadding: Dp = 8.dp,
    val noteBodyInputPadding: Dp = 8.dp,
    val noteTitleInputRadius: Dp = 12.dp,
    val noteInputBoxPadding: Dp = 4.dp,
    val noteCardListPadding: Dp = 4.dp,
)

val LocalSize = compositionLocalOf { Size() }

val MaterialTheme.size: Size
    @Composable
    @ReadOnlyComposable
    get() = LocalSize.current