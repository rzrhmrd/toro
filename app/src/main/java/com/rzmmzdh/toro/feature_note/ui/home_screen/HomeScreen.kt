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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold(
        modifier = modifier,
        topBar = { SearchableTopBar(viewModel.searchQuery.value, viewModel) },
        bottomBar = {
            MainNavigationBar(navController, currentScreen = Screen.Home)
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { paddingValues ->
        NoteList(paddingValues, viewModel, navController)
        if (viewModel.showNoteDeletionNotification.value) {
            DeletedNoteNotification(viewModel, snackBarHostState)
        }

    }
}

@Composable
fun DeletedNoteNotification(viewModel: HomeScreenViewModel, snackbarHostState: SnackbarHostState) {
    LaunchedEffect(key1 = viewModel.showNoteDeletionNotification) {
        val noteDeletedMessage =
            snackbarHostState.showSnackbar(message = "یادداشت حذف شد.",
                actionLabel = "برگشت",
                duration = SnackbarDuration.Long)
        when (noteDeletedMessage) {
            SnackbarResult.Dismissed -> viewModel.onEvent(HomeScreenEvent.NotificationDisplayed)
            SnackbarResult.ActionPerformed -> viewModel.onEvent(HomeScreenEvent.UndoDeletedNote)
        }

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
            NoteItem(viewModel, note, navController)
        }
    }
}

@Composable
fun NoteItem(
    viewModel: HomeScreenViewModel,
    note: Note,
    navController: NavController,
) {
    Card(modifier = Modifier
        .clickable {
            navController.navigate(Screen.EditNote.withNoteId(note.id))
        }
        .size(MaterialTheme.size.noteCard)
        .padding(8.dp)
    ) {
        DeleteNoteButton(viewModel, note)
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
fun DeleteNoteButton(viewModel: HomeScreenViewModel, note: Note) {
    Box(contentAlignment = Alignment.TopStart, modifier = Modifier
        .fillMaxWidth()
        .padding(start = 4.dp, top = 4.dp)
    ) {
        IconButton(onClick = { viewModel.onEvent(HomeScreenEvent.DeleteNote(note)) },
            modifier = Modifier.size(20.dp)) {
            Icon(Icons.Rounded.Close, null, modifier = Modifier.size(16.dp))
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
