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
import com.rzmmzdh.toro.feature_note.ui.core.component.ToroNavigationBar
import com.rzmmzdh.toro.feature_note.ui.core.navigateTo
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
        topBar = {
            SearchableTopBar(
                title = viewModel.searchQuery.value,
                onValueChange = { viewModel.onEvent(HomeScreenEvent.Search(it)) },
                clearSearchIconVisible = viewModel.searchQuery.value.isNotBlank(),
                onCloseSearchIconClick = { viewModel.onEvent(HomeScreenEvent.ClearSearchBox) }
            )
        },
        bottomBar = {
            ToroNavigationBar(navController = navController, currentScreen = Screen.Home)
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { paddingValues ->
        NoteList(
            paddingValues = paddingValues,
            notes = viewModel.notes.value.notes,
            onNoteDelete = { viewModel.onEvent(HomeScreenEvent.DeleteNote(it)) },
            onNoteClick = {
                navController.navigateTo(Screen.EditNote.withNoteId(it.id))
            },
        )
        if (viewModel.showNoteDeletionNotification.value) {
            NoteDeleteNotification(
                key = { viewModel.showNoteDeletionNotification },
                onDismiss = { viewModel.onEvent(HomeScreenEvent.NotificationDisplayed) },
                onAction = { viewModel.onEvent(HomeScreenEvent.UndoDeletedNote) },
                snackbarHostState = snackBarHostState
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableTopBar(
    title: String,
    onValueChange: (String) -> Unit,
    clearSearchIconVisible: Boolean = false,
    onCloseSearchIconClick: () -> Unit,
) {
    CenterAlignedTopAppBar(title = {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = title,
            onValueChange = { onValueChange(it) },
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
                    visible = clearSearchIconVisible,
                ) {
                    Icon(
                        Icons.Rounded.Close,
                        null,
                        modifier = Modifier.clickable(onClick = onCloseSearchIconClick)
                    )
                }
            }
        )
    })
}

@Composable
fun NoteList(
    paddingValues: PaddingValues,
    notes: List<Note>,
    onNoteClick: (Note) -> Unit,
    onNoteDelete: (Note) -> Unit,
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
        items(items = notes) {
            NoteItem(note = it,
                onDelete = { onNoteDelete(it) },
                onClick = { onNoteClick(it) }
            )
        }
    }
}

@Composable
fun NoteItem(
    note: Note,
    onDelete: () -> Unit,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .size(MaterialTheme.size.noteCard)
            .padding(8.dp)
            .clickable(onClick = onClick)

    ) {
        Box(
            contentAlignment = Alignment.TopStart, modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            DeleteNoteButton(onDelete)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.space.noteItemPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                NoteItemTitle(note)
                NoteItemBody(note)
            }

        }
    }
}

@Composable
fun DeleteNoteButton(
    onDeleteIconClick: () -> Unit,
) {
    IconButton(
        onClick = onDeleteIconClick,
        modifier = Modifier.size(20.dp)
    ) {
        Icon(Icons.Rounded.Close, null, modifier = Modifier.size(16.dp))
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

@Composable
fun NoteDeleteNotification(
    key: Any,
    onDismiss: () -> Unit,
    onAction: () -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    LaunchedEffect(key1 = key) {
        val noteDeletedMessage =
            snackbarHostState.showSnackbar(
                message = "یادداشت حذف شد.",
                actionLabel = "برگشت",
                duration = SnackbarDuration.Long
            )
        when (noteDeletedMessage) {
            SnackbarResult.Dismissed -> onDismiss()
            SnackbarResult.ActionPerformed -> onAction()
        }

    }
}

