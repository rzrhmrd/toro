package com.rzmmzdh.toro.feature_note.ui.core

import androidx.navigation.NavController

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