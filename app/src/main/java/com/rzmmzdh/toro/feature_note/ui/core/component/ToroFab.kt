package com.rzmmzdh.toro.feature_note.ui.core.component

import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.runtime.Composable

@Composable
fun ToroFab(onClick: () -> Unit, icon: @Composable () -> Unit) {
    LargeFloatingActionButton(onClick = onClick, content = icon)
}
