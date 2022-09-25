package com.rzmmzdh.toro.feature_note.ui.home_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rzmmzdh.toro.R
import com.rzmmzdh.toro.feature_note.domain.model.Note
import com.rzmmzdh.toro.feature_note.ui.core.Screen
import com.rzmmzdh.toro.feature_note.ui.core.component.MainNavigationBar
import com.rzmmzdh.toro.theme.size
import com.rzmmzdh.toro.theme.space
import com.rzmmzdh.toro.theme.style

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavController,
) {
    Scaffold(
        modifier = modifier,
        topBar = { SearchableTopBar(viewModel.searchQuery.value, viewModel) },
        bottomBar = {
            MainNavigationBar(navController, currentScreen = Screen.Home)
        }
    ) { paddingValues ->
        NoteList(paddingValues, viewModel, navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableTopBar(title: String, viewModel: HomeScreenViewModel) {
    CenterAlignedTopAppBar(title = {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = title,
            onValueChange = { viewModel.onEvent(HomeScreenEvent.OnSearch(it)) },
            textStyle = MaterialTheme.style.searchableTopBarText,
            placeholder = {
                Text(
                    stringResource(id = R.string.toro_title),
                    style = MaterialTheme.style.topBarTitle,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
            singleLine = true,
            trailingIcon = {
                AnimatedVisibility(
                    visible = viewModel.searchQuery.value.isNotBlank(),
                ) {
                    Icon(Icons.Rounded.Close,
                        null,
                        modifier = Modifier.clickable { viewModel.onEvent(HomeScreenEvent.ClearSearchBox) })
                }
            }
        )
    })
}

@Composable
fun NoteList(
    paddingValues: PaddingValues,
    viewModel: HomeScreenViewModel,
    navController: NavController,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding(),
                start = MaterialTheme.size.noteCardListPadding,
                end = MaterialTheme.size.noteCardListPadding
            )
    ) {
        items(items = viewModel.notes.value.notes) { note ->
            NoteItem(note, navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteItem(
    note: Note,
    navController: NavController,
) {
    Card(
        modifier = Modifier
            .size(MaterialTheme.size.noteItem)
            .padding(8.dp),
        onClick = { navController.navigate(Screen.EditNote.withNoteId(note.id)) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.space.noteItemsPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            NoteItemTitle(note)
            NoteItemBody(note)
        }

    }
}

@Composable
fun NoteItemTitle(note: Note) {
    Text(
        note.title,
        style = MaterialTheme.style.noteCardTitle, maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun NoteItemBody(note: Note) {
    Text(
        note.body,
        style =
        MaterialTheme.style.noteCardBody,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis

    )
}
