package com.practicum.ghiblicollection.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.ghiblicollection.domain.model.Film
import com.practicum.ghiblicollection.domain.usecase.GetFavoriteByIdUseCase
import com.practicum.ghiblicollection.domain.usecase.GetFilmByIdUseCase
import com.practicum.ghiblicollection.domain.usecase.IsFavoriteUseCase
import com.practicum.ghiblicollection.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FilmDetailViewModel @Inject constructor(
    private val getFilmByIdUseCase: GetFilmByIdUseCase,
    private val getFavoriteByIdUseCase: GetFavoriteByIdUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<FilmDetailUiState>(FilmDetailUiState.Loading)
    val uiState: StateFlow<FilmDetailUiState> = _uiState.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    private var currentFilm: Film? = null

    fun loadFilm(filmId: String) {
        viewModelScope.launch {
            _uiState.value = FilmDetailUiState.Loading

            val favorite = getFavoriteByIdUseCase(filmId)
            if (favorite != null) {
                currentFilm = favorite
                _uiState.value = FilmDetailUiState.Success(favorite)
                observeFavorite(filmId)
                return@launch
            }

            try {
                val film = getFilmByIdUseCase(filmId)
                currentFilm = film
                _uiState.value = if (film != null) {
                    FilmDetailUiState.Success(film)
                } else {
                    FilmDetailUiState.Error("Фильм не найден")
                }
                observeFavorite(filmId)
            } catch (e: IOException) {
                _uiState.value = FilmDetailUiState.Error(
                    "Проблема с сетью. Проверьте интернет-соединение."
                )
            } catch (e: Exception) {
                _uiState.value = FilmDetailUiState.Error(
                    e.localizedMessage ?: "Не удалось загрузить фильм"
                )
            }
        }
    }

    private fun observeFavorite(filmId: String) {
        viewModelScope.launch {
            isFavoriteUseCase(filmId).collect { favorite ->
                _isFavorite.value = favorite
            }
        }
    }

    fun toggleFavorite() {
        val film = currentFilm ?: return
        viewModelScope.launch {
            toggleFavoriteUseCase(film)
        }
    }
}