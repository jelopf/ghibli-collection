package com.practicum.ghiblicollection.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.ghiblicollection.data.cache.FilterBadgeCache
import com.practicum.ghiblicollection.domain.model.Film
import com.practicum.ghiblicollection.domain.model.FilmFilter
import com.practicum.ghiblicollection.domain.usecase.FilterFilmsUseCase
import com.practicum.ghiblicollection.domain.usecase.GetFilmsUseCase
import com.practicum.ghiblicollection.domain.usecase.GetFilterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FilmListViewModel @Inject constructor(
    private val getFilmsUseCase: GetFilmsUseCase,
    private val getFilterUseCase: GetFilterUseCase,
    private val filterFilmsUseCase: FilterFilmsUseCase,
    private val badgeCache: FilterBadgeCache
) : ViewModel() {

    private val _uiState = MutableStateFlow<FilmListUiState>(FilmListUiState.Loading)
    val uiState: StateFlow<FilmListUiState> = _uiState.asStateFlow()

    val hasActiveFilters: StateFlow<Boolean> = badgeCache.hasActiveFilters

    private var allFilms: List<Film> = emptyList()
    private var currentFilter: FilmFilter = FilmFilter.EMPTY

    init {
        observeFilter()
        loadFilms()
        syncBadge()
    }

    private fun observeFilter() {
        viewModelScope.launch {
            getFilterUseCase().collectLatest { filter ->
                currentFilter = filter
                applyFilter()
            }
        }
    }

    fun loadFilms() {
        viewModelScope.launch {
            _uiState.value = FilmListUiState.Loading
            try {
                allFilms = getFilmsUseCase()
                applyFilter()
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

    private fun applyFilter() {
        if (allFilms.isEmpty()) {
            return
        }
        val filtered = filterFilmsUseCase(allFilms, currentFilter)
        _uiState.value = FilmListUiState.Success(filtered)
    }

    private fun syncBadge() {
        viewModelScope.launch {
            val filter = getFilterUseCase().first()
            badgeCache.setHasActiveFilters(filter.isActive)
        }
    }
}