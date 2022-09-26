package com.rzmmzdh.toro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rzmmzdh.toro.feature_note.ui.core.Screen
import com.rzmmzdh.toro.feature_note.ui.edit_note_screen.EditNoteScreen
import com.rzmmzdh.toro.feature_note.ui.home_screen.HomeScreen
import com.rzmmzdh.toro.theme.TOROTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = !isSystemInDarkTheme()
            HideSystemBar(systemUiController, useDarkIcons)
            TOROTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route
                    ) {
                        composable(Screen.Home.route) {
                            HomeScreen(navController = navController)
                        }
                        composable(route = Screen.EditNote.route + "?editNoteId={noteId}",
                            arguments = listOf(navArgument("noteId") {
                                type = NavType.IntType
                                defaultValue = -1
                            })) {
                            EditNoteScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun HideSystemBar(
        systemUiController: SystemUiController,
        useDarkIcons: Boolean,
    ) {
        SideEffect {
            systemUiController.setSystemBarsColor(color = Color.Transparent,
                darkIcons = useDarkIcons)
        }
    }
}