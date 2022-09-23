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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rzmmzdh.toro.R
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
        topBar = { MainTopBar(stringResource(R.string.toro)) },
        bottomBar = {
            MainNavigationBar(navController, currentScreen = Screens.Home)
        }
    ) { paddingValues ->
        NoteList(paddingValues, viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(title: String) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                title,
                style = TextStyle(
                    fontFamily = vazirFontFamily,
                    fontWeight = FontWeight.Black,
                    fontSize = 24.sp,
                    textDirection = TextDirection.ContentOrRtl
                )
            )
        },
    )
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
            Card(
                modifier = Modifier
                    .size(192.dp)
                    .padding(8.dp)
                    .animateItemPlacement(),
                onClick = { viewModel.onEvent(HomeScreenEvent.DeleteNote(note)) }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        note.title,
                        style = TextStyle(
                            fontFamily = vazirFontFamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textDirection = TextDirection.ContentOrRtl,
                            textAlign = TextAlign.Center
                        ), maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        note.body,
                        style =
                        TextStyle(
                            fontFamily = vazirFontFamily,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            textDirection = TextDirection.ContentOrRtl,
                            textAlign = TextAlign.Center
                        ),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis

                    )
                }

            }
        }
    }
}