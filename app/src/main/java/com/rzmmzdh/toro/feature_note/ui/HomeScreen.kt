package com.rzmmzdh.toro.feature_note.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rzmmzdh.toro.R
import com.rzmmzdh.toro.feature_note.ui.core.Screens
import com.rzmmzdh.toro.feature_note.ui.viewmodel.HomeScreenEvent
import com.rzmmzdh.toro.feature_note.ui.viewmodel.HomeScreenViewModel
import com.rzmmzdh.toro.theme.vazirFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    Scaffold(
        modifier = modifier,
        topBar = { MainTopBar(stringResource(R.string.toro)) },
        bottomBar = {
            MainNavigationBar(navController, currentScreen = Screens.Home)
        }
    ) { paddingValues ->
        NoteList(paddingValues, viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(title: String) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                title,
                style = TextStyle(
                    fontFamily = vazirFontFamily,
                    fontWeight = FontWeight.Black,
                    fontSize = 24.sp
                )
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteList(
    paddingValues: PaddingValues,
    viewModel: HomeScreenViewModel,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding(),
            )
            .padding(horizontal = 8.dp),
    ) {
        itemsIndexed(items = viewModel.notes.value.notes) { index, note ->
            Card(
                modifier = Modifier
                    .size(192.dp)
                    .padding(all = 8.dp),
                onClick = { viewModel.onEvent(HomeScreenEvent.DeleteNote(note)) }
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        viewModel.notes.value.notes[index].title,
                        style = TextStyle(
                            fontFamily = vazirFontFamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        viewModel.notes.value.notes[index].body,
                        style =
                        TextStyle(
                            fontFamily = vazirFontFamily,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )

                    )
                }

            }
        }
    }
}

@Composable
fun MainNavigationBar(navController: NavController, currentScreen: Screens) {
    var selectedDestination by remember { mutableStateOf(currentScreen) }
    val items = listOf(Screens.Settings, Screens.Home, Screens.EditNote)
    NavigationBar(modifier = Modifier) {
        items.forEach { item ->
            when (item) {
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

            }
        }
    }
}
