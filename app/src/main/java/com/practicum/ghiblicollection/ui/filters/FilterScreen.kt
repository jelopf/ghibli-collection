package com.practicum.ghiblicollection.ui.filters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.practicum.ghiblicollection.ui.theme.GhibliCollectionTheme

@Composable
fun FilterScreen(
    onBack: () -> Unit,
    onSaved: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FilterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            onSaved()
        }
    }

    FilterContent(
        uiState = uiState,
        onDirectorChange = viewModel::onDirectorChange,
        onMinRatingChange = viewModel::onMinRatingChange,
        onYearFromChange = viewModel::onYearFromChange,
        onYearToChange = viewModel::onYearToChange,
        onSave = viewModel::save,
        onReset = viewModel::reset,
        onBack = onBack,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterContent(
    uiState: FilterUiState,
    onDirectorChange: (String) -> Unit,
    onMinRatingChange: (String) -> Unit,
    onYearFromChange: (String) -> Unit,
    onYearToChange: (String) -> Unit,
    onSave: () -> Unit,
    onReset: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Фильтры") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Режиссёр",
                style = MaterialTheme.typography.titleSmall
            )
            OutlinedTextField(
                value = uiState.director,
                onValueChange = onDirectorChange,
                placeholder = { Text("Например, Miyazaki") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Минимальный рейтинг",
                style = MaterialTheme.typography.titleSmall
            )
            OutlinedTextField(
                value = uiState.minRating,
                onValueChange = onMinRatingChange,
                placeholder = { Text("Например, 90") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Год выпуска",
                style = MaterialTheme.typography.titleSmall
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = uiState.yearFrom,
                    onValueChange = onYearFromChange,
                    label = { Text("От") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = uiState.yearTo,
                    onValueChange = onYearToChange,
                    label = { Text("До") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onSave,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Готово")
            }

            OutlinedButton(
                onClick = onReset,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Сбросить")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun FilterContentEmptyPreview() {
    GhibliCollectionTheme {
        FilterContent(
            uiState = FilterUiState(),
            onDirectorChange = {},
            onMinRatingChange = {},
            onYearFromChange = {},
            onYearToChange = {},
            onSave = {},
            onReset = {},
            onBack = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FilterContentFilledPreview() {
    GhibliCollectionTheme {
        FilterContent(
            uiState = FilterUiState(
                director = "Miyazaki",
                minRating = "90",
                yearFrom = "1990",
                yearTo = "2010"
            ),
            onDirectorChange = {},
            onMinRatingChange = {},
            onYearFromChange = {},
            onYearToChange = {},
            onSave = {},
            onReset = {},
            onBack = {}
        )
    }
}