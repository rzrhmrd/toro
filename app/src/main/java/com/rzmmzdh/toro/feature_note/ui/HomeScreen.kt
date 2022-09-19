package com.rzmmzdh.toro.feature_note.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rzmmzdh.toro.feature_note.viewmodel.HomeScreenViewModel
import com.rzmmzdh.toro.theme.vazirFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeScreenViewModel = hiltViewModel()) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            AddNoteFab(viewModel)
        },
        topBar = { MainTopBar(viewModel) },
        bottomBar = {
            MainNavigationBar()
        }
    ) { paddingValues ->
        NoteList(paddingValues, viewModel)
    }
}

@Composable
fun MainNavigationBar() {
    val selectedDestination by remember {
        mutableStateOf(Destination.HomeScreen)
    }
    val items = listOf(Destination.HomeScreen, Destination.Settings)
    NavigationBar(modifier = Modifier) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedDestination == item,
                onClick = { /*TODO*/ },
                icon = {
                    when (item) {
                        Destination.Settings -> Icon(
                            Icons.Rounded.Settings,
                            "settings screen navigation item"
                        )
                        Destination.HomeScreen -> Icon(
                            Icons.Rounded.Home,
                            "home screen navigation item"
                        )


                    }
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(viewModel: HomeScreenViewModel) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                "تورو",
                style = TextStyle(
                    fontFamily = vazirFontFamily,
                    fontWeight = FontWeight.Black,
                    fontSize = 24.sp
                )
            )
        },
        actions = {
            IconButton(onClick = { viewModel.onEvent(HomeScreenEvent.DeleteAllNotes) }) {
                Icon(Icons.Rounded.Delete, "delete all notes")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteList(
    paddingValues: PaddingValues,
    viewModel: HomeScreenViewModel,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding(),
                start = 8.dp,
                end = 8.dp
            ),
    ) {
        itemsIndexed(items = viewModel.noteList.value.notes) { index, note ->
            Card(
                modifier = Modifier
                    .size(192.dp)
                    .padding(all = 8.dp),
                onClick = { /*TODO*/ }
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        viewModel.noteList.value.notes[index].title,
                        style = TextStyle(
                            fontFamily = vazirFontFamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        viewModel.noteList.value.notes[index].body,
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
fun AddNoteFab(viewModel: HomeScreenViewModel) {
    ExtendedFloatingActionButton(
        onClick = { viewModel.onEvent(HomeScreenEvent.AddNewNote) },
        icon = { Icon(Icons.Rounded.Add, "add note fab") },
        text = {
            Text(
                "یادداشت",
                style = TextStyle(
                    fontFamily = vazirFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
        }
    )
}