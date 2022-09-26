package com.rzmmzdh.toro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rzmmzdh.toro.feature_note.ui.core.Screen
import com.rzmmzdh.toro.feature_note.ui.edit_note_screen.EditNoteScreen
import com.rzmmzdh.toro.feature_note.ui.home_screen.HomeScreen
import com.rzmmzdh.toro.theme.TOROTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalAnimationApi::class)
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
                    val navController = rememberAnimatedNavController()
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = Screen.Home.route
                    ) {
                        composable(route = Screen.Home.route,
                            enterTransition = {
                                when (initialState.destination.route) {
                                    Screen.EditNote.route -> fadeIn()
                                    else -> null
                                }
                            },
                            exitTransition = {
                                when (targetState.destination.route) {
                                    Screen.EditNote.route -> fadeOut()
                                    else -> null
                                }
                            }) {
                            HomeScreen(navController = navController)
                        }
                        composable(
                            route = Screen.EditNote.route + "?editNoteId={noteId}",
                            arguments = listOf(navArgument("noteId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }),
                            enterTransition = {
                                when (initialState.destination.route) {
                                    Screen.Home.route -> fadeIn()
                                    else -> null
                                }
                            },
                            exitTransition = {
                                when (targetState.destination.route) {
                                    Screen.Home.route -> fadeOut()
                                    else -> null
                                }
                            }
                        ) {
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