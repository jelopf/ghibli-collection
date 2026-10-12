package com.practicum.ghiblicollection.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.ghiblicollection.domain.usecase.GetFilmsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FilmListViewModel @Inject constructor(
    private val getFilmsUseCase: GetFilmsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<FilmListUiState>(FilmListUiState.Loading)
    val uiState: StateFlow<FilmListUiState> = _uiState.asStateFlow()

    init {
        loadFilms()
    }

    fun loadFilms() {
        viewModelScope.launch {
            _uiState.value = FilmListUiState.Loading
            try {
                val films = getFilmsUseCase()
                _uiState.value = FilmListUiState.Success(films)
            } catch (e: IOException) {
                _uiState.value = FilmListUiState.Error(
                    "Проблема с сетью. Проверьте интернет-соединение."
                )
            } catch (e: Exception) {
                _uiState.value = FilmListUiState.Error(
                    e.localizedMessage ?: "Не удалось загрузить список"
                )
            }
        }
    }
}