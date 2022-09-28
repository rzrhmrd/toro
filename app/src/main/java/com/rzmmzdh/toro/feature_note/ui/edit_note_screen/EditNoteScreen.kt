package com.rzmmzdh.toro.feature_note.ui.edit_note_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rzmmzdh.toro.R
import com.rzmmzdh.toro.feature_note.ui.core.Constant.errorMessages
import com.rzmmzdh.toro.feature_note.ui.core.Screen
import com.rzmmzdh.toro.feature_note.ui.core.component.ToroNavigationBar
import com.rzmmzdh.toro.feature_note.ui.core.navigateTo
import com.rzmmzdh.toro.theme.size
import com.rzmmzdh.toro.theme.style
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(viewModel: EditNoteViewModel = hiltViewModel(), navController: NavController) {
    Scaffold(
        topBar = {
            EditNoteTopBar(title = viewModel.category.value, onCategoryClick = {
                viewModel.onEvent(EditNoteEvent.CategorySelected(it))
            })
        },
        floatingActionButton = {
            SaveNoteFab(onClick = {
                viewModel.onEvent(EditNoteEvent.SaveNote)
                if (!viewModel.showAlert.value) {
                    navController.navigateTo(
                        route = Screen.Home.route)
                }
            })
        },
        bottomBar = {
            ToroNavigationBar(
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
private fun EditNoteTopBar(title: String, onCategoryClick: (NoteCategory) -> Unit) {
    val menuExpanded = remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    onClick = { menuExpanded.value = !menuExpanded.value },
                ) {
                    Icon(Icons.Rounded.KeyboardArrowDown, null, modifier = Modifier.size(24.dp))
                }
                Text(
                    text = title,
                    style = MaterialTheme.style.topBarTitle,
                )
            }
            DropdownMenu(
                expanded = menuExpanded.value,
                onDismissRequest = { menuExpanded.value = !menuExpanded.value },
            ) {
                val categories = NoteCategory.values()
                categories.forEach {
                    DropdownMenuItem(text = {
                        Text(
                            text = it.title,
                            style = MaterialTheme.style.categoryList,
                            modifier = Modifier.fillMaxSize()
                        )
                    }, onClick = {
                        onCategoryClick(it)
                        menuExpanded.value = !menuExpanded.value
                    })
                }

            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun EditNoteBody(paddingValues: PaddingValues, viewModel: EditNoteViewModel) {
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
        NoteTitleInput(viewModel.currentNote.value.title) {
            viewModel.onEvent(EditNoteEvent.OnTitleChanged(it))
        }
        NoteBodyInput(viewModel.currentNote.value.body) {
            viewModel.onEvent(EditNoteEvent.OnBodyChanged(it))
        }
    }
    EmptyInputError(viewModel)

}

@Composable
private fun EmptyInputError(viewModel: EditNoteViewModel) {
    if (viewModel.showAlert.value) {
        AlertDialog(
            onDismissRequest = {
                viewModel.onEvent(EditNoteEvent.AlertShown)
            },
            icon = { Icon(Icons.Rounded.Info, null) },
            title = {
                Text(
                    text = errorMessages[Random.nextInt(until = errorMessages.size)],
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
private fun NoteTitleInput(value: String, onValueChange: (String) -> Unit) {
    TextField(
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
        modifier = Modifier
            .fillMaxWidth()
            .height(MaterialTheme.size.noteTitleInputHeight)
            .padding(MaterialTheme.size.noteTitleInputPadding),
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun NoteBodyInput(value: String, onValueChange: (String) -> Unit) {
    TextField(
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
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.size.noteBodyInputPadding),
    )
}

@Composable
private fun SaveNoteFab(
    onClick: () -> Unit,
) {
    LargeFloatingActionButton(
        onClick = onClick,
    ) {
        Icon(
            Icons.Rounded.Check,
            null,
            modifier = Modifier.size(36.dp)
        )
    }
}
