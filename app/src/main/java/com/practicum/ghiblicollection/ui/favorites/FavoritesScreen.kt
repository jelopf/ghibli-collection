package com.practicum.ghiblicollection.ui.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.practicum.ghiblicollection.domain.model.Film
import com.practicum.ghiblicollection.ui.components.FilmListItem
import com.practicum.ghiblicollection.ui.components.LoadingState
import com.practicum.ghiblicollection.ui.theme.GhibliCollectionTheme

@Composable
fun FavoritesScreen(
    onFilmClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    FavoritesContent(
        uiState = uiState,
        onFilmClick = onFilmClick,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavoritesContent(
    uiState: FavoritesUiState,
    onFilmClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Избранное") })
        }
    ) { innerPadding ->
        when (uiState) {
            is FavoritesUiState.Loading -> LoadingState(Modifier.padding(innerPadding))
            is FavoritesUiState.Success -> {
                if (uiState.films.isEmpty()) {
                    EmptyFavorites(modifier = Modifier.padding(innerPadding))
                } else {
                    FavoritesList(
                        films = uiState.films,
                        onFilmClick = onFilmClick,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
private fun FavoritesList(
    films: List<Film>,
    onFilmClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(films, key = { it.id }) { film ->
            FilmListItem(film = film, onClick = { onFilmClick(film.id) })
        }
    }
}

@Composable
private fun EmptyFavorites(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "В избранном пока пусто.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun FavoritesContentEmptyPreview() {
    GhibliCollectionTheme {
        FavoritesContent(
            uiState = FavoritesUiState.Success(emptyList()),
            onFilmClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoritesContentFilledPreview() {
    GhibliCollectionTheme {
        FavoritesContent(
            uiState = FavoritesUiState.Success(
                listOf(
                    Film(
                        id = "1",
                        title = "Spirited Away",
                        originalTitle = "千と千尋の神隠し",
                        originalTitleRomanised = "Sen to Chihiro no Kamikakushi",
                        image = "",
                        movieBanner = "",
                        description = "Описание",
                        director = "Hayao Miyazaki",
                        producer = "Toshio Suzuki",
                        releaseDate = "2001",
                        runningTime = "124",
                        rtScore = "97"
                    )
                )
            ),
            onFilmClick = {}
        )
    }
}