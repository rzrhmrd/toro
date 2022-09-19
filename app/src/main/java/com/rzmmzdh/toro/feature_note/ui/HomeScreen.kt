package com.rzmmzdh.toro.feature_note.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.rzmmzdh.toro.feature_note.viewmodel.HomeScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeScreenViewModel = hiltViewModel()) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            AddNoteFab(viewModel)
        },
        topBar = { MainTopBar(viewModel) }
    ) { paddingValues ->
        NoteList(paddingValues, viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(viewModel: HomeScreenViewModel) {
    CenterAlignedTopAppBar(
        title = { Text("toro") },
        actions = {
            IconButton(onClick = { viewModel.onEvent(HomeScreenEvent.DeleteAllNotes) }) {
                Icon(Icons.Rounded.Delete, "delete all notes")
            }
        }
    )
}

@Composable
fun NoteList(
    paddingValues: PaddingValues,
    viewModel: HomeScreenViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding()),
        ) {
            items(items = viewModel.noteList.value.notes) {
                Text(it.title, textAlign = TextAlign.Center)
            }
        }

    }
}

@Composable
fun AddNoteFab(viewModel: HomeScreenViewModel) {
    FloatingActionButton(onClick = { viewModel.onEvent(HomeScreenEvent.AddNewNote) }) {
        Icon(Icons.Rounded.Add, contentDescription = "Add a new note")
    }
}