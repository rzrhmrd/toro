package com.rzmmzdh.toro.feature_note.ui.edit_note_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rzmmzdh.toro.R
import com.rzmmzdh.toro.feature_note.ui.core.Constant.editNoteTitles
import com.rzmmzdh.toro.feature_note.ui.core.Screen
import com.rzmmzdh.toro.feature_note.ui.core.component.MainNavigationBar
import com.rzmmzdh.toro.theme.size
import com.rzmmzdh.toro.theme.style
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(viewModel: EditNoteViewModel = hiltViewModel(), navController: NavController) {
    Scaffold(
        topBar = { EditNoteTopBar(viewModel, navController) },
        floatingActionButton = { SaveNoteFab(viewModel, navController) },
        bottomBar = {
            MainNavigationBar(
                navController = navController,
                currentScreen = Screen.EditNote
            )
        }
    ) { paddingValues ->
        EditNoteBody(paddingValues = paddingValues, viewModel)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteTopBar(viewModel: EditNoteViewModel, navController: NavController) {
    TopAppBar(title = { EditNoteTopBarTitle(titles = editNoteTitles) },
        modifier = Modifier.fillMaxWidth())
}

@Composable
fun EditNoteTopBarTitle(titles: ArrayList<String>) {
    Text(
        titles[Random.nextInt(titles.size)],
        style = MaterialTheme.style.topBarTitle, modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun EditNoteBody(paddingValues: PaddingValues, viewModel: EditNoteViewModel) {
    Column(
        modifier = Modifier
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
        NoteTitleInput(viewModel)
        NoteBodyInput(viewModel)
    }
    EmptyInputError(viewModel)

}

@Composable
fun EmptyInputError(viewModel: EditNoteViewModel) {
    if (viewModel.showAlert.value) {
        AlertDialog(
            onDismissRequest = {
                viewModel.onEvent(EditNoteEvent.AlertShown)
            },
            icon = { Icon(Icons.Rounded.Info, null) },
            title = {
                Text(
                    text = viewModel.errorMessages[Random.nextInt(until = viewModel.errorMessages.size)],
                    style = MaterialTheme.style.errorBoxTitle,
                    textAlign = TextAlign.Center,
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.onEvent(EditNoteEvent.AlertShown)
                    }
                ) {
                    Text("باشه", style = MaterialTheme.style.errorBoxButton)
                }
            },
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NoteTitleInput(viewModel: EditNoteViewModel) {
    TextField(
        value = viewModel.currentNote.value.title,
        onValueChange = { viewModel.onEvent(EditNoteEvent.OnTitleChanged(it)) },
        textStyle = MaterialTheme.style.noteTitleInputValue,
        placeholder = {
            Text(
                stringResource(id = R.string.subject),
                style = MaterialTheme.style.noteTitleInputPlaceholder,
                modifier = Modifier.fillMaxWidth()
            )
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(MaterialTheme.size.noteTitleInputHeight)
            .padding(MaterialTheme.size.noteTitleInputPadding),
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NoteBodyInput(viewModel: EditNoteViewModel) {
    TextField(
        value = viewModel.currentNote.value.body,
        onValueChange = { viewModel.onEvent(EditNoteEvent.OnBodyChanged(it)) },
        textStyle = MaterialTheme.style.noteBodyInputValue,
        placeholder = {
            Text(
                stringResource(R.string.body),
                style = MaterialTheme.style.noteBodyInputPlaceholder,
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.size.noteBodyInputPadding),
    )
}

@Composable
fun SaveNoteFab(viewModel: EditNoteViewModel, navController: NavController) {
    LargeFloatingActionButton(
        onClick = {
            if (viewModel.currentNote.value.title.isNotBlank() || viewModel.currentNote.value.body.isNotBlank()) {
                viewModel.onEvent(EditNoteEvent.SaveNote)
                navController.navigate(Screen.Home.route)
            } else {
                viewModel.onEvent(EditNoteEvent.ShowAlert)
            }
        },
    ) {
        Icon(Icons.Rounded.Check,
            null,
            modifier = Modifier.size(36.dp))
    }
}
