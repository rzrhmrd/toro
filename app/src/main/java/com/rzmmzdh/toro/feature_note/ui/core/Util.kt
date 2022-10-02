package com.rzmmzdh.toro.feature_note.ui.core

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