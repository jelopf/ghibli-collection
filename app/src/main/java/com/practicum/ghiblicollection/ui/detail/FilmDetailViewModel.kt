package com.practicum.ghiblicollection.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.ghiblicollection.domain.usecase.GetFilmByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FilmDetailViewModel @Inject constructor(
    private val getFilmByIdUseCase: GetFilmByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<FilmDetailUiState>(FilmDetailUiState.Loading)
    val uiState: StateFlow<FilmDetailUiState> = _uiState.asStateFlow()

    fun loadFilm(filmId: String) {
        viewModelScope.launch {
            _uiState.value = FilmDetailUiState.Loading
            try {
                val film = getFilmByIdUseCase(filmId)
                _uiState.value = if (film != null) {
                    FilmDetailUiState.Success(film)
                } else {
                    FilmDetailUiState.Error("Фильм не найден")
                }
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
}