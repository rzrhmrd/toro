package com.rzmmzdh.toro.feature_note.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
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
import com.rzmmzdh.toro.feature_note.domain.model.Note
import com.rzmmzdh.toro.feature_note.ui.core.Screens
import com.rzmmzdh.toro.feature_note.ui.viewmodel.EditNoteEvent
import com.rzmmzdh.toro.feature_note.ui.viewmodel.EditNoteViewModel
import com.rzmmzdh.toro.theme.vazirFontFamily
import java.util.*
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

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TitleTextField(viewModel: EditNoteViewModel) {
    TextField(
        value = viewModel.titleText.value,
        onValueChange = { viewModel.onEvent(EditNoteEvent.OnTitleChanged(it)) },
        textStyle = TextStyle(
            fontFamily = vazirFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            textDirection = TextDirection.ContentOrRtl,
            textAlign = TextAlign.Center
        ),
        placeholder = {
            Text(
                stringResource(R.string.title),
                style = TextStyle(
                    fontFamily = vazirFontFamily,
                    fontWeight = FontWeight.Normal,
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
                    fontWeight = FontWeight.Normal,
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
    ExtendedFloatingActionButton(
        onClick = {
            viewModel.onEvent(
                EditNoteEvent.SaveNote(
                    Note(
                        id = Random.nextInt(),
                        title = viewModel.titleText.value,
                        body = viewModel.bodyText.value,
                        Date()
                    )
                )
            )
            navController.navigate(Screens.Home.route)
        },
        icon = { Icon(Icons.Rounded.Check, stringResource(R.string.save_note_fab)) },
        text = {
            Text(
                stringResource(R.string.ok),
                style = TextStyle(
                    fontFamily = vazirFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )
        }
    )
}