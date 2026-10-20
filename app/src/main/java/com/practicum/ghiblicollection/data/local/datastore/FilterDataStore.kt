package com.practicum.ghiblicollection.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.practicum.ghiblicollection.domain.model.FilmFilter
import com.practicum.ghiblicollection.domain.repository.FilterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.filterDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "filter_prefs"
)

class FilterDataStore @Inject constructor(
    private val context: Context
) : FilterRepository {

    private val dataStore = context.filterDataStore

    override val filter: Flow<FilmFilter> = dataStore.data.map { prefs ->
        FilmFilter(
            director = prefs[KEY_DIRECTOR] ?: "",
            minRating = prefs[KEY_MIN_RATING] ?: 0,
            yearFrom = prefs[KEY_YEAR_FROM] ?: 0,
            yearTo = prefs[KEY_YEAR_TO] ?: 0
        )
    }

    override suspend fun saveFilter(filter: FilmFilter) {
        dataStore.edit { prefs ->
            prefs[KEY_DIRECTOR] = filter.director
            prefs[KEY_MIN_RATING] = filter.minRating
            prefs[KEY_YEAR_FROM] = filter.yearFrom
            prefs[KEY_YEAR_TO] = filter.yearTo
        }
    }

    override suspend fun clearFilter() {
        dataStore.edit { prefs ->
            prefs.remove(KEY_DIRECTOR)
            prefs.remove(KEY_MIN_RATING)
            prefs.remove(KEY_YEAR_FROM)
            prefs.remove(KEY_YEAR_TO)
        }
    }

    companion object {
        private val KEY_DIRECTOR = stringPreferencesKey("director")
        private val KEY_MIN_RATING = intPreferencesKey("min_rating")
        private val KEY_YEAR_FROM = intPreferencesKey("year_from")
        private val KEY_YEAR_TO = intPreferencesKey("year_to")
    }
}