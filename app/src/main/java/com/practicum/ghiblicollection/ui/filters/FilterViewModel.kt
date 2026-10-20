package com.practicum.ghiblicollection.ui.filters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.ghiblicollection.data.cache.FilterBadgeCache
import com.practicum.ghiblicollection.domain.model.FilmFilter
import com.practicum.ghiblicollection.domain.usecase.ClearFilterUseCase
import com.practicum.ghiblicollection.domain.usecase.GetFilterUseCase
import com.practicum.ghiblicollection.domain.usecase.SaveFilterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val getFilterUseCase: GetFilterUseCase,
    private val saveFilterUseCase: SaveFilterUseCase,
    private val clearFilterUseCase: ClearFilterUseCase,
    private val badgeCache: FilterBadgeCache
) : ViewModel() {

    private val _uiState = MutableStateFlow(FilterUiState())
    val uiState: StateFlow<FilterUiState> = _uiState.asStateFlow()

    init {
        loadCurrentFilter()
    }

    private fun loadCurrentFilter() {
        viewModelScope.launch {
            val current = getFilterUseCase().first()
            _uiState.value = FilterUiState.fromFilter(current)
            badgeCache.setHasActiveFilters(current.isActive)
        }
    }

    fun onDirectorChange(value: String) {
        _uiState.value = _uiState.value.copy(director = value)
    }

    fun onMinRatingChange(value: String) {
        _uiState.value = _uiState.value.copy(minRating = value.filter { it.isDigit() })
    }

    fun onYearFromChange(value: String) {
        _uiState.value = _uiState.value.copy(yearFrom = value.filter { it.isDigit() })
    }

    fun onYearToChange(value: String) {
        _uiState.value = _uiState.value.copy(yearTo = value.filter { it.isDigit() })
    }

    fun save() {
        viewModelScope.launch {
            val filter = _uiState.value.toFilmFilter()
            saveFilterUseCase(filter)
            badgeCache.setHasActiveFilters(filter.isActive)
            _uiState.value = _uiState.value.copy(isSaved = true)
        }
    }

    fun reset() {
        viewModelScope.launch {
            clearFilterUseCase()
            badgeCache.setHasActiveFilters(false)
            _uiState.value = FilterUiState()
        }
    }
}