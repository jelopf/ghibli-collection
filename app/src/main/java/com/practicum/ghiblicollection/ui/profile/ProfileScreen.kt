package com.practicum.ghiblicollection.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.practicum.ghiblicollection.ui.components.AvatarCircle
import com.practicum.ghiblicollection.ui.components.LoadingState
import com.practicum.ghiblicollection.ui.theme.GhibliCollectionTheme

@Composable
fun ProfileScreen(
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    ProfileContent(
        uiState = uiState,
        onEditClick = onEditClick,
        onResumeClick = viewModel::onResumeClick,
        modifier = modifier
    )
}

@Composable
private fun ProfileContent(
    uiState: ProfileUiState,
    onEditClick: () -> Unit,
    onResumeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is ProfileUiState.Loading -> LoadingState(modifier)
        is ProfileUiState.Success -> ProfileContentFilled(
            profile = uiState.profile,
            onEditClick = onEditClick,
            onResumeClick = onResumeClick,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileContentFilled(
    profile: ProfileUiModel,
    onEditClick: () -> Unit,
    onResumeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Профиль") },
                actions = {
                    IconButton(onClick = onEditClick) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Редактировать профиль"
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            AvatarCircle(
                avatarPath = profile.avatarPath,
                hasAvatar = profile.hasAvatar,
                placeholder = profile.avatarPlaceholder
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = profile.fullName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onResumeClick,
                enabled = profile.hasResume,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Резюме")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ProfileContentEmptyPreview() {
    GhibliCollectionTheme {
        ProfileContentFilled(
            profile = ProfileUiModel(
                fullName = "Имя не указано",
                avatarPath = "",
                hasAvatar = false,
                avatarPlaceholder = "?",
                resumeUrl = "",
                hasResume = false
            ),
            onEditClick = {},
            onResumeClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileContentFilledPreview() {
    GhibliCollectionTheme {
        ProfileContentFilled(
            profile = ProfileUiModel(
                fullName = "Иван Иванов",
                avatarPath = "",
                hasAvatar = false,
                avatarPlaceholder = "И",
                resumeUrl = "https://example.com/resume.pdf",
                hasResume = true
            ),
            onEditClick = {},
            onResumeClick = {}
        )
    }
}