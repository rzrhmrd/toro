package com.rzmmzdh.toro.feature_note.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rzmmzdh.toro.R
import com.rzmmzdh.toro.feature_note.ui.core.Screens
import com.rzmmzdh.toro.feature_note.ui.core.component.MainNavigationBar
import com.rzmmzdh.toro.feature_note.ui.viewmodel.EditNoteEvent
import com.rzmmzdh.toro.feature_note.ui.viewmodel.EditNoteViewModel
import com.rzmmzdh.toro.theme.vazirFontFamily
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(viewModel: EditNoteViewModel = hiltViewModel(), navController: NavController) {
    Scaffold(
        topBar = { MainTopBar(stringResource(R.string.new_note)) },
        floatingActionButton = { SaveNoteFab(viewModel, navController) },
        bottomBar = {
            MainNavigationBar(
                navController = navController,
                currentScreen = Screens.EditNote
            )
        }
    ) { paddingValues ->
        EditNote(paddingValues = paddingValues, viewModel)

    }
}

@Composable
fun EditNote(paddingValues: PaddingValues, viewModel: EditNoteViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleTextField(viewModel)
        BodyTextField(viewModel)
    }
    EmptyInputError(viewModel)

}

@Composable
fun EmptyInputError(viewModel: EditNoteViewModel) {
    if (viewModel.openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                viewModel.openDialog.value = !viewModel.openDialog.value
            },
            icon = { Icon(Icons.Rounded.Info, null) },
            title = {
                Text(
                    text = viewModel.errorMessages[Random.nextInt(until = viewModel.errorMessages.size)],
                    style = TextStyle(
                        fontFamily = vazirFontFamily,
                        textDirection = TextDirection.ContentOrRtl,
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        fontWeight = MaterialTheme.typography.headlineLarge.fontWeight
                    ),
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.openDialog.value = !viewModel.openDialog.value
                    }
                ) {
                    Text("باشه", style = TextStyle(
                        fontFamily = vazirFontFamily,
                        textDirection = TextDirection.ContentOrRtl,
                        fontSize = MaterialTheme.typography.labelLarge.fontSize,
                        fontWeight = MaterialTheme.typography.labelLarge.fontWeight
                    ))
                }
            },
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TitleTextField(viewModel: EditNoteViewModel) {
    TextField(
        value = viewModel.titleText.value,
        onValueChange = { viewModel.onEvent(EditNoteEvent.OnTitleChanged(it)) },
        textStyle = TextStyle(
            fontFamily = vazirFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            textDirection = TextDirection.ContentOrRtl,
            textAlign = TextAlign.Center
        ),
        placeholder = {
            Text(
                stringResource(R.string.subject),
                style = TextStyle(
                    fontFamily = vazirFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    textDirection = TextDirection.ContentOrRtl,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp)
            .padding(8.dp)
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun BodyTextField(viewModel: EditNoteViewModel) {
    TextField(
        value = viewModel.bodyText.value,
        onValueChange = { viewModel.onEvent(EditNoteEvent.OnBodyChanged(it)) },
        textStyle = TextStyle(
            fontFamily = vazirFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            textDirection = TextDirection.ContentOrRtl,
            textAlign = TextAlign.Center
        ),
        placeholder = {
            Text(
                stringResource(R.string.body),
                style = TextStyle(
                    fontFamily = vazirFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    textDirection = TextDirection.ContentOrRtl,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    )
}

@Composable
fun SaveNoteFab(viewModel: EditNoteViewModel, navController: NavController) {
    LargeFloatingActionButton(
        onClick = {
            if (viewModel.titleText.value.isNotBlank() || viewModel.bodyText.value.isNotBlank()) {
                viewModel.onEvent(EditNoteEvent.SaveNote)
                navController.navigate(Screens.Home.route)
            } else {
                viewModel.onEvent(EditNoteEvent.OpenDialog)
            }
        },
    ) {
        Text(
            stringResource(id = R.string.save),
            style = TextStyle(
                fontFamily = vazirFontFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}
