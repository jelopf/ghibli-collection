package com.practicum.ghiblicollection.ui.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.practicum.ghiblicollection.ui.components.FilmListItem
import com.practicum.ghiblicollection.ui.theme.GhibliCollectionTheme

@Composable
fun FilmListScreen(
    onFilmClick: (String) -> Unit,
    viewModel: FilmListViewModel = viewModel()
) {
    val films by viewModel.films.collectAsState()

    LazyColumn {
        items(films, key = { it.id }) { film ->
            FilmListItem(
                film = film,
                onClick = { onFilmClick(film.id) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FilmListScreenPreview() {
    GhibliCollectionTheme {
        FilmListScreen(onFilmClick = {})
    }
}