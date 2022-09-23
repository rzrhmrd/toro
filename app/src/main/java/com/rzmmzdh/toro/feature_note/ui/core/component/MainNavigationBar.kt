package com.rzmmzdh.toro.feature_note.ui.core.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.rzmmzdh.toro.R
import com.rzmmzdh.toro.feature_note.ui.core.Screens

@Composable
fun MainNavigationBar(navController: NavController, currentScreen: Screens) {
    var selectedDestination by remember { mutableStateOf(currentScreen) }
    val items = listOf(Screens.EditNote, Screens.Home)
    NavigationBar(modifier = Modifier) {
        items.forEach { item ->
            when (item) {
                Screens.Home -> NavigationBarItem(
                    selected = selectedDestination == item,
                    onClick = { navController.navigate(Screens.Home.route) },
                    icon = { Icon(Icons.Rounded.Home, stringResource(R.string.home_screen_icon)) }
                )
                Screens.EditNote -> NavigationBarItem(
                    selected = selectedDestination == item,
                    onClick = { navController.navigate(Screens.EditNote.route) },
                    icon = { Icon(Icons.Rounded.Edit, stringResource(R.string.edit_note_icon)) }
                )
                Screens.Settings -> NavigationBarItem(
                    selected = selectedDestination == item,
                    onClick = { navController.navigate(Screens.Settings.route) },
                    icon = {
                        Icon(
                            Icons.Rounded.Settings,
                            stringResource(R.string.settings_icon)
                        )
                    }
                )

            }
        }
    }
}