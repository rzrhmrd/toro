package com.rzmmzdh.toro.feature_note.ui.home_screen

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rzmmzdh.toro.R
import com.rzmmzdh.toro.feature_note.domain.model.Note
import com.rzmmzdh.toro.feature_note.ui.core.Screen
import com.rzmmzdh.toro.feature_note.ui.core.component.ToroNavigationBar
import com.rzmmzdh.toro.feature_note.ui.core.navigateTo
import com.rzmmzdh.toro.feature_note.ui.edit_note_screen.NoteCategory
import com.rzmmzdh.toro.theme.size
import com.rzmmzdh.toro.theme.space
import com.rzmmzdh.toro.theme.style
import com.rzmmzdh.toro.theme.vazirFontFamily
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeScreenViewModel = hiltViewModel(),
    navController: NavController,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val noteListState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val inputFocusManager = LocalFocusManager.current
    Scaffold(
        modifier = modifier,
        topBar = {
            SearchableTopBar(
                title = state.searchQuery.value,
                onValueChange = {
                    state.onEvent(HomeScreenEvent.Search(it))
                },
                clearSearchIconVisible = state.clearSearchIconVisible.value,
                onCloseSearchIconClick = {
                    state.onEvent(HomeScreenEvent.ClearSearchBox)
                    coroutineScope.launch {
                        noteListState.animateScrollToItem(0)
                    }
                    inputFocusManager.clearFocus()
                },
            )
        },
        bottomBar = {
            ToroNavigationBar(navController = navController, currentScreen = Screen.Home)
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { paddingValues ->
        Content(
            paddingValues = paddingValues,
            state = state,
            noteListState = noteListState,
            onFilterItemSelected = {
                state.onEvent(HomeScreenEvent.OnFilterItemSelected(it))
                coroutineScope.launch { noteListState.animateScrollToItem(0) }
            }, onClearFilter = {
                state.onEvent(HomeScreenEvent.OnClearFilter)
                coroutineScope.launch {
                    noteListState.animateScrollToItem(0)
                }
            }, onNoteClick = {
                navController.navigateTo(
                    Screen.EditNote.withNoteId(
                        it.id
                    )
                )
            }
        )
        if (state.showNoteDeletionNotification.value) {
            NoteDeleteNotification(
                key = { state.showNoteDeletionNotification },
                onDismiss = { state.onEvent(HomeScreenEvent.NotificationDisplayed) },
                onAction = { state.onEvent(HomeScreenEvent.UndoDeletedNote) },
                snackbarHostState = snackBarHostState
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchableTopBar(
    title: String,
    onValueChange: (String) -> Unit,
    clearSearchIconVisible: Boolean = false,
    onCloseSearchIconClick: () -> Unit
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
                        modifier = Modifier.clickable(onClick = {
                            onCloseSearchIconClick()
                        })
                    )
                }
            }
        )
    })
}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    state: HomeScreenViewModel,
    noteListState: LazyGridState,
    onFilterItemSelected: (NoteCategory) -> Unit,
    onClearFilter: () -> Unit,
    onNoteClick: (Note) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = BottomCenter) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {
            NoteFilter(state, onFilterItemSelected)
            NoteList(
                listState = noteListState,
                notes = state.notes.value.notes,
                onNoteClick = onNoteClick,
                onNoteDelete = { state.onEvent(HomeScreenEvent.DeleteNote(it)) },
            )
        }
        ClearFilter(state, paddingValues, onClearFilter)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ClearFilter(
    state: HomeScreenViewModel,
    paddingValues: PaddingValues,
    onClearFilter: () -> Unit
) {
    AnimatedVisibility(
        visible = state.clearFilterButtonVisible.value,
    ) {
        ElevatedFilterChip(
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
            selected = false,
            onClick = onClearFilter,
//            border = InputChipDefaults.inputChipBorder(borderColor = MaterialTheme.colorScheme.tertiary),
            label = {
                Text(
                    "پاک کردن فیلتر 🧹",
                    style = TextStyle(
                        fontFamily = vazirFontFamily,
                        textDirection = TextDirection.ContentOrRtl,
                        textAlign = TextAlign.Center, fontSize = 14.sp
                    )
                )
            })
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun NoteFilter(
    state: HomeScreenViewModel,
    onFilterItemSelected: (NoteCategory) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(start = 4.dp, end = 8.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = CenterVertically,
    ) {
        NoteCategory.values().forEach { category ->
            ElevatedFilterChip(
                modifier = Modifier.padding(4.dp),
                label = {
                    Text(
                        category.title,
                        style = TextStyle(
                            fontFamily = vazirFontFamily,
                            textDirection = TextDirection.ContentOrRtl,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        )
                    )
                },
                selected = (category == state.selectedCategory.value),
                onClick = { onFilterItemSelected(category) },
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NoteList(
    listState: LazyGridState,
    notes: List<Note>,
    onNoteClick: (Note) -> Unit,
    onNoteDelete: (Note) -> Unit,
) {
    LazyVerticalGrid(
        state = listState,
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = MaterialTheme.size.noteCardListPadding,
                end = MaterialTheme.size.noteCardListPadding
            )
    ) {
        items(items = notes, key = { it.id }) {
            Card(
                modifier = Modifier
                    .size(MaterialTheme.size.noteCard)
                    .padding(8.dp)
                    .animateItemPlacement()
                    .clickable(onClick = { onNoteClick(it) }
                    )

            ) {
                Box(
                    contentAlignment = Alignment.TopStart, modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                ) {
                    DeleteNoteButton(onDeleteIconClick = { onNoteDelete(it) })
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(MaterialTheme.space.noteItemPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        NoteItemTitle(note = it)
                        NoteItemBody(note = it)
                    }

                }
            }
        }
    }
}

@Composable
private fun DeleteNoteButton(
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
private fun NoteItemTitle(note: Note) {
    Text(
        note.title,
        style = MaterialTheme.style.noteCardTitle, maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun NoteItemBody(note: Note) {
    Text(
        note.body,
        style =
        MaterialTheme.style.noteCardBody,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis

    )
}

@Composable
private fun NoteDeleteNotification(
    key: Any,
    onDismiss: () -> Unit,
    onAction: () -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    LaunchedEffect(key1 = key) {
        val noteDeletedMessage =
            snackbarHostState.showSnackbar(
                message = "Note deleted.",
                actionLabel = "Undo",
                duration = SnackbarDuration.Long
            )
        when (noteDeletedMessage) {
            SnackbarResult.Dismissed -> onDismiss()
            SnackbarResult.ActionPerformed -> onAction()
        }

    }
}

