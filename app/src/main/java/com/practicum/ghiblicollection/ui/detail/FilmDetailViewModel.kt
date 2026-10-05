package com.practicum.ghiblicollection.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.ghiblicollection.data.FilmRepository
import com.practicum.ghiblicollection.domain.model.Film
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FilmDetailViewModel : ViewModel() {

    private val repository = FilmRepository()

    private val _film = MutableStateFlow<Film?>(null)
    val film: StateFlow<Film?> = _film.asStateFlow()

    fun loadFilm(filmId: String) {
        viewModelScope.launch {
            _film.value = repository.getFilmById(filmId)
        }
    }
}