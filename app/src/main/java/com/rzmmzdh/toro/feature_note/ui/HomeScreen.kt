package com.rzmmzdh.toro.feature_note.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rzmmzdh.toro.R
import com.rzmmzdh.toro.feature_note.domain.model.Note
import com.rzmmzdh.toro.feature_note.ui.core.Screens
import com.rzmmzdh.toro.feature_note.ui.core.component.MainNavigationBar
import com.rzmmzdh.toro.feature_note.ui.viewmodel.HomeScreenEvent
import com.rzmmzdh.toro.feature_note.ui.viewmodel.HomeScreenViewModel
import com.rzmmzdh.toro.theme.vazirFontFamily

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
            MainNavigationBar(navController, currentScreen = Screens.Home)
        }
    ) { paddingValues ->
        NoteList(paddingValues, viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableTopBar(title: String, viewModel: HomeScreenViewModel) {
    CenterAlignedTopAppBar(title = {
        TextField(
            value = title,
            onValueChange = { viewModel.onEvent(HomeScreenEvent.OnSearch(it)) },
            modifier = Modifier
                .fillMaxWidth(),
            textStyle = TextStyle(fontFamily = vazirFontFamily,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                textDirection = TextDirection.ContentOrRtl,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center),
            placeholder = {
                Text(
                    stringResource(id = R.string.toro_title),
                    style = TextStyle(fontFamily = vazirFontFamily,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                        textDirection = TextDirection.ContentOrRtl,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center), modifier = Modifier.fillMaxWidth()
                )
            },
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
            singleLine = true)
    })
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NoteList(
    paddingValues: PaddingValues,
    viewModel: HomeScreenViewModel,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding(),
            )
    ) {
        items(items = viewModel.notes.value.notes) { note ->
            NoteItem(viewModel, note)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NoteItem(
    viewModel: HomeScreenViewModel,
    note: Note,
) {
    Card(
        modifier = Modifier
            .size(192.dp)
            .padding(8.dp),
        onClick = { viewModel.onEvent(HomeScreenEvent.DeleteNote(note)) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
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
        style = TextStyle(
            fontFamily = vazirFontFamily,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
            textDirection = TextDirection.ContentOrRtl,
            textAlign = TextAlign.Center
        ), maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun NoteItemBody(note: Note) {
    Text(
        note.body,
        style =
        TextStyle(
            fontFamily = vazirFontFamily,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
            textDirection = TextDirection.ContentOrRtl,
            textAlign = TextAlign.Center
        ),
        maxLines = 3,
        overflow = TextOverflow.Ellipsis

    )
}
