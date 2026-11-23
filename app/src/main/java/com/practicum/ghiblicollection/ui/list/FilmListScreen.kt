package com.practicum.ghiblicollection.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.practicum.ghiblicollection.ui.components.ErrorState
import com.practicum.ghiblicollection.ui.components.FilmListItem
import com.practicum.ghiblicollection.ui.components.LoadingState
import com.practicum.ghiblicollection.ui.theme.GhibliCollectionTheme

@Composable
fun FilmListScreen(
    onFilmClick: (String) -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FilmListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val hasActiveFilters by viewModel.hasActiveFilters.collectAsState()

    FilmListContent(
        uiState = uiState,
        hasActiveFilters = hasActiveFilters,
        onFilmClick = onFilmClick,
        onRetry = { viewModel.loadFilms() },
        onFilterClick = onFilterClick,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilmListContent(
    uiState: FilmListUiState,
    hasActiveFilters: Boolean,
    onFilmClick: (String) -> Unit,
    onRetry: () -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Фильмы") },
            actions = {
                BadgedBox(
                    badge = {
                        if (hasActiveFilters) {
                            Badge(
                                modifier = Modifier.offset(x = (-15).dp, y = 10.dp)
                            )
                        }
                    }
                ) {
                    IconButton(onClick = onFilterClick) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Фильтры"
                        )
                    }
                }
            }
        )

        when (uiState) {
            is FilmListUiState.Loading -> LoadingState(Modifier.weight(1f))
            is FilmListUiState.Success -> FilmsList(
                films = uiState.films,
                onFilmClick = onFilmClick,
                modifier = Modifier.weight(1f)
            )
            is FilmListUiState.Error -> ErrorState(
                message = uiState.message,
                onRetry = onRetry,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun FilmsList(
    films: List<Film>,
    onFilmClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (films.isEmpty()) {
        EmptyListState(modifier)
        return
    }

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

@Composable
private fun EmptyListState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Ничего не найдено. Попробуйте изменить фильтры.",
            textAlign = TextAlign.Center
        )
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
    )
)

@Preview(showBackground = true)
@Composable
private fun FilmListContentSuccessPreview() {
    GhibliCollectionTheme {
        FilmListContent(
            uiState = FilmListUiState.Success(previewFilms),
            onFilmClick = {},
            onRetry = {},
            hasActiveFilters = false,
            onFilterClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FilmListContentEmptyPreview() {
    GhibliCollectionTheme {
        FilmListContent(
            uiState = FilmListUiState.Success(emptyList()),
            onFilmClick = {},
            onRetry = {},
            hasActiveFilters = true,
            onFilterClick = {}
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
            onRetry = {},
            hasActiveFilters = true,
            onFilterClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FilmListContentErrorPreview() {
    GhibliCollectionTheme {
        FilmListContent(
            uiState = FilmListUiState.Error("Проблема с сетью"),
            onFilmClick = {},
            onRetry = {},
            hasActiveFilters = false,
            onFilterClick = {}
        )
    }
}