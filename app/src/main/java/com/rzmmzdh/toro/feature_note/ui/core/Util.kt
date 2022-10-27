package com.rzmmzdh.toro.feature_note.ui.core

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

/**
Navigate to route. By default clears the backstack.
 */
fun NavController.navigateTo(route: String, clearBackStack: Boolean = true) {
    navigate(route) {
        if (clearBackStack) {
            popUpTo(
                currentBackStackEntry?.destination?.route
                    ?: return@navigate
            ) {
                inclusive = true
            }
        }
    }


}
@Composable
fun colorTransition(
    initialColor: Color,
    targetColor: Color,
    tweenAnimationDuration: Int = 1000,
    repeatMode: RepeatMode = RepeatMode.Reverse
): Color {
    val infiniteTransition = rememberInfiniteTransition()
    val colorTransition by infiniteTransition.animateColor(
        initialValue = initialColor,
        targetValue = targetColor,
        animationSpec = infiniteRepeatable(
            animation = tween(tweenAnimationDuration),
            repeatMode = repeatMode
        )
    )
    return colorTransition
}