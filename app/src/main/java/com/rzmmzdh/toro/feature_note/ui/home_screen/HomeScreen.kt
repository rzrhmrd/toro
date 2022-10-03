package com.rzmmzdh.toro.feature_note.ui.home_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
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
                title = state.search.value.searchQuery,
                onValueChange = {
                    state.onEvent(HomeScreenEvent.Search(it))
                },
                clearSearchIconVisible = state.search.value.isClearSearchIconVisible,
                onCloseSearchIconClick = {
                    state.onEvent(HomeScreenEvent.ClearSearchBox)
                    coroutineScope.launch {
                        noteListState.animateScrollToItem(0)
                    }
                    inputFocusManager.clearFocus()
                }, onSearch = {
                    state.onEvent(HomeScreenEvent.Search(it))
                    inputFocusManager.clearFocus()
                }
            )
        },
        bottomBar = {
            ToroNavigationBar(navController = navController, currentScreen = Screen.Home)
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { paddingValues ->
        Notes(
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
        if (state.showNoteDeleteNotification.value) {
            NoteDeleteNotification(
                key = { state.showNoteDeleteNotification },
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
    onCloseSearchIconClick: () -> Unit,
    onSearch: (String) -> Unit
) {
    CenterAlignedTopAppBar(title = {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = title,
            onValueChange = { onValueChange(it) },
            textStyle = MaterialTheme.style.searchableTopBarText,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch(title) }),
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
private fun Notes(
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
            label = {
                Text(
                    stringResource(R.string.clear_filter),
                    style = MaterialTheme.style.clearFilter
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
            .horizontalScroll(rememberScrollState(), reverseScrolling = true),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = CenterVertically,
    ) {
        NoteCategory.values().forEach { category ->
            ElevatedFilterChip(
                modifier = Modifier.padding(4.dp),
                label = {
                    Text(
                        category.title,
                        style = MaterialTheme.style.categoryItem
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
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    NoteTag(tag = it.category.title)
                    NoteDeleteButton(onDeleteIconClick = { onNoteDelete(it) })
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(MaterialTheme.space.noteItemPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        NoteTitle(note = it)
                        NoteBody(note = it)
                    }
                }

            }
        }
    }
}

@Composable
private fun NoteTag(tag: String) {
    Box(
        modifier = Modifier
            .fillMaxSize(), contentAlignment = BottomEnd
    ) {
        Text(
            tag.substringAfter(delimiter = stringResource(R.string.white_space)).trim(),
            modifier = Modifier
                .alpha(0.5F)
                .padding(end = 4.dp),
            style = MaterialTheme.style.noteTag,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun NoteDeleteButton(
    onDeleteIconClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(), contentAlignment = TopStart
    ) {
        IconButton(
            onClick = onDeleteIconClick,
            modifier = Modifier
                .size(18.dp)
        ) {
            Icon(
                Icons.Rounded.Close, null, modifier = Modifier
                    .size(18.dp)
                    .alpha(0.5F)
            )
        }
    }
}

@Composable
private fun NoteTitle(note: Note) {
    Text(
        note.title,
        style = MaterialTheme.style.noteCardTitle, maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun NoteBody(note: Note) {
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

