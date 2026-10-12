package com.practicum.ghiblicollection.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.practicum.ghiblicollection.domain.model.Film
import com.practicum.ghiblicollection.ui.components.ErrorState
import com.practicum.ghiblicollection.ui.components.FilmListItem
import com.practicum.ghiblicollection.ui.components.LoadingState
import com.practicum.ghiblicollection.ui.theme.GhibliCollectionTheme

@Composable
fun FilmListScreen(
    onFilmClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FilmListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    FilmListContent(
        uiState = uiState,
        onFilmClick = onFilmClick,
        onRetry = { viewModel.loadFilms() },
        modifier = modifier
    )
}

@Composable
private fun FilmListContent(
    uiState: FilmListUiState,
    onFilmClick: (String) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is FilmListUiState.Loading -> LoadingState(modifier)
        is FilmListUiState.Success -> FilmsList(
            films = uiState.films,
            onFilmClick = onFilmClick,
            modifier = modifier
        )
        is FilmListUiState.Error -> ErrorState(
            message = uiState.message,
            onRetry = onRetry,
            modifier = modifier
        )
    }
}

@Composable
private fun FilmsList(
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
            FilmListItem(
                film = film,
                onClick = { onFilmClick(film.id) }
            )
        }
    }
}


private val previewFilms = listOf(
    Film(
        id = "1",
        title = "Spirited Away",
        originalTitle = "千と千尋の神隠し",
        originalTitleRomanised = "Sen to Chihiro no Kamikakushi",
        image = "",
        movieBanner = "",
        description = "A young girl wanders into a world of spirits.",
        director = "Hayao Miyazaki",
        producer = "Toshio Suzuki",
        releaseDate = "2001",
        runningTime = "124",
        rtScore = "97"
    ),
    Film(
        id = "2",
        title = "My Neighbor Totoro",
        originalTitle = "となりのトトロ",
        originalTitleRomanised = "Tonari no Totoro",
        image = "",
        movieBanner = "",
        description = "Two sisters meet magical forest spirits.",
        director = "Hayao Miyazaki",
        producer = "Toru Hara",
        releaseDate = "1988",
        runningTime = "86",
        rtScore = "93"
    ),
    Film(
        id = "3",
        title = "Princess Mononoke",
        originalTitle = "もののけ姫",
        originalTitleRomanised = "Mononoke Hime",
        image = "",
        movieBanner = "",
        description = "A prince journeys to the west to find a cure.",
        director = "Hayao Miyazaki",
        producer = "Toshio Suzuki",
        releaseDate = "1997",
        runningTime = "134",
        rtScore = "93"
    )
)

@Preview(showBackground = true)
@Composable
private fun FilmListContentSuccessPreview() {
    GhibliCollectionTheme {
        FilmListContent(
            uiState = FilmListUiState.Success(previewFilms),
            onFilmClick = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FilmListContentLoadingPreview() {
    GhibliCollectionTheme {
        FilmListContent(
            uiState = FilmListUiState.Loading,
            onFilmClick = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FilmListContentErrorPreview() {
    GhibliCollectionTheme {
        FilmListContent(
            uiState = FilmListUiState.Error("Не удалось загрузить данные"),
            onFilmClick = {},
            onRetry = {}
        )
    }
}