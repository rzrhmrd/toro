package com.rzmmzdh.toro.feature_note.ui.edit_note_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.icons.sharp.Done
import androidx.compose.material.icons.sharp.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rzmmzdh.toro.R
import com.rzmmzdh.toro.feature_note.ui.core.Constant.emptyNoteAlertMessages
import com.rzmmzdh.toro.feature_note.ui.core.Screen
import com.rzmmzdh.toro.feature_note.ui.core.colorTransition
import com.rzmmzdh.toro.feature_note.ui.core.navigateTo
import com.rzmmzdh.toro.theme.size
import com.rzmmzdh.toro.theme.style
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    modifier: Modifier = Modifier,
    state: EditNoteViewModel = hiltViewModel(),
    navController: NavController
) {
    val inputFocusManager = LocalFocusManager.current
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            EditNoteTopBar(title = state.currentNote.value.category.title, onCategorySelect = {
                state.onEvent(EditNoteEvent.OnCategorySelect(it))
            }, onSave = {
                state.onEvent(EditNoteEvent.OnNoteSave)
                inputFocusManager.clearFocus()
                if (!state.currentNote.value.isEmpty) {
                    navController.navigateTo(
                        route = Screen.Home.route
                    )
                }
            })
        },
    ) { paddingValues ->
        EditNoteBody(
            paddingValues = paddingValues,
            title = state.currentNote.value.title,
            onTitleValueChange = { state.onEvent(EditNoteEvent.OnTitleChange(it)) },
            body = state.currentNote.value.body,
            onBodyValueChange = { state.onEvent(EditNoteEvent.OnBodyChange(it)) },
            isNoteEmpty = state.currentNote.value.isEmpty,
            onEmptyNoteAlertDismiss = { state.onEvent(EditNoteEvent.OnAlertDismiss) }
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditNoteTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onCategorySelect: (NoteCategory) -> Unit,
    onSave: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }
    CenterAlignedTopAppBar(
        modifier = modifier.fillMaxWidth(),
        actions = {
            IconButton(onClick = { onSave() }) {
                Icon(
                    Icons.Sharp.Done,
                    stringResource(R.string.save_button),
                    modifier = Modifier.size(32.dp),
                    tint = colorTransition(
                        initialColor = MaterialTheme.colorScheme.primary,
                        targetColor = MaterialTheme.colorScheme.tertiary,
                        tweenAnimationDuration = 5000
                    )
                )
            }
        },
        title = {
            Title(title = title, onClick = { menuExpanded = !menuExpanded })
            Categories(
                menuExpanded = menuExpanded,
                onCategoryClick = {
                    onCategorySelect(it)
                    menuExpanded = !menuExpanded
                },
                onDismissRequest = { menuExpanded = !menuExpanded }
            )
        }
    )
}

@Composable
private fun Title(modifier: Modifier = Modifier, title: String, onClick: () -> Unit) {
    Text(
        modifier = modifier
            .clickable { onClick() }
            .padding(8.dp),
        text = title,
        style = MaterialTheme.style.topBarTitle,
    )
}

@Composable
private fun Categories(
    modifier: Modifier = Modifier,
    menuExpanded: Boolean,
    onCategoryClick: (NoteCategory) -> Unit,
    onDismissRequest: () -> Unit
) {
    DropdownMenu(
        expanded = menuExpanded,
        onDismissRequest = { onDismissRequest() },
    ) {

        val categories = NoteCategory.values()
        categories.forEach { category ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = category.title,
                        style = MaterialTheme.style.categoryList,
                        modifier = Modifier.fillMaxSize()
                    )
                }, onClick = {
                    onCategoryClick(category)
                })
        }

    }
}

@Composable
private fun EditNoteBody(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    title: String,
    onTitleValueChange: (String) -> Unit,
    body: String,
    onBodyValueChange: (String) -> Unit,
    isNoteEmpty: Boolean,
    onEmptyNoteAlertDismiss: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding(),
                start = MaterialTheme.size.noteInputBoxPadding,
                end = MaterialTheme.size.noteInputBoxPadding
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NoteTitleInput(value = title, onValueChange = onTitleValueChange)
        NoteBodyInput(
            value = body,
            onValueChange = onBodyValueChange,
        )
    }
    EmptyNoteAlert(
        isNoteEmpty = isNoteEmpty,
        onDismiss = onEmptyNoteAlertDismiss
    )

}

@Composable
private fun EmptyNoteAlert(
    modifier: Modifier = Modifier,
    isNoteEmpty: Boolean,
    onDismiss: () -> Unit
) {
    if (isNoteEmpty) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = onDismiss,
            icon = { Icon(Icons.Sharp.Info, null) },
            title = {
                Text(
                    text = emptyNoteAlertMessages[Random.nextInt(until = emptyNoteAlertMessages.size)],
                    style = MaterialTheme.style.errorBoxTitle,
                    textAlign = TextAlign.Center,
                )
            },
            confirmButton = {
                TextButton(
                    onClick = onDismiss
                ) {
                    Text(stringResource(R.string.ok), style = MaterialTheme.style.errorBoxButton)
                }
            },
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun NoteTitleInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
    val inputFocusManager = LocalFocusManager.current
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(MaterialTheme.size.noteTitleInputHeight)
            .padding(MaterialTheme.size.noteTitleInputPadding),
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.style.noteTitleInputValue,
        placeholder = {
            Text(
                stringResource(id = R.string.subject),
                style = MaterialTheme.style.noteTitleInputPlaceholder,
                modifier = Modifier.fillMaxWidth()
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { inputFocusManager.moveFocus(FocusDirection.Down) }),
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun NoteBodyInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
) {
    val noteBodyScrollState = rememberScrollState()
    TextField(
        modifier = modifier
            .fillMaxSize()
            .padding(MaterialTheme.size.noteBodyInputPadding)
            .scrollable(noteBodyScrollState, Orientation.Vertical)
            .imePadding(),
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.style.noteBodyInputValue,
        placeholder = {
            Text(
                stringResource(R.string.body),
                style = MaterialTheme.style.noteBodyInputPlaceholder,
                modifier = Modifier.fillMaxWidth()
            )
        },
    )
}