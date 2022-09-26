package com.rzmmzdh.toro.feature_note.ui.core.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.rzmmzdh.toro.R
import com.rzmmzdh.toro.feature_note.ui.core.Screen

@Composable
fun MainNavigationBar(navController: NavController, currentScreen: Screen) {
    val selectedDestination by remember { mutableStateOf(currentScreen) }
    val items = listOf(Screen.EditNote, Screen.Home)
    NavigationBar(modifier = Modifier) {
        items.forEach { item ->
            when (item) {
                is Screen.Home -> NavigationBarItem(
                    selected = selectedDestination == item,
                    onClick = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(navController.currentBackStackEntry?.destination?.route
                                ?: return@navigate) {
                                inclusive = true
                            }
                        }
                    },
                    icon = { Icon(Icons.Rounded.Home, stringResource(R.string.home_screen_icon)) }
                )
                is Screen.EditNote -> NavigationBarItem(
                    selected = selectedDestination == item,
                    onClick = {
                        navController.navigate(Screen.EditNote.route) {
                            popUpTo(navController.currentBackStackEntry?.destination?.route
                                ?: return@navigate) {
                                inclusive = true
                            }

                        }
                    },
                    icon = { Icon(Icons.Rounded.Edit, stringResource(R.string.edit_note_icon)) }
                )
            }
        }
    }
}