package com.practicum.ghiblicollection.ui.profileedit

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.practicum.ghiblicollection.ui.components.AvatarCircle
import com.practicum.ghiblicollection.ui.components.LoadingState
import com.practicum.ghiblicollection.ui.theme.GhibliCollectionTheme
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun ProfileEditScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileEditViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var showPickerDialog by remember { mutableStateOf(false) }
    var pendingCameraUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) viewModel.onAvatarPicked(uri)
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            pendingCameraUri?.let { viewModel.onAvatarPicked(it) }
        }
        pendingCameraUri = null
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val uri = createTempImageUri(context)
            pendingCameraUri = uri
            cameraLauncher.launch(uri)
        } else {
            viewModel.onCameraPermissionDenied()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is ProfileEditEvent.NavigateBack -> onBack()
                is ProfileEditEvent.ShowCameraPermissionDenied -> {
                    scope.launch {
                        snackbarHostState.showSnackbar("Нужен доступ к камере")
                    }
                }
            }
        }
    }

    ProfileEditContent(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onBack = onBack,
        onNameChange = viewModel::onNameChange,
        onResumeUrlChange = viewModel::onResumeUrlChange,
        onAvatarClick = { showPickerDialog = true },
        onDoneClick = viewModel::onDoneClick,
        modifier = modifier
    )

    if (showPickerDialog) {
        AvatarPickerDialog(
            onDismiss = { showPickerDialog = false },
            onGalleryClick = {
                showPickerDialog = false
                galleryLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },
            onCameraClick = {
                showPickerDialog = false
                val hasPermission = ContextCompat.checkSelfPermission(
                    context, Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
                if (hasPermission) {
                    val uri = createTempImageUri(context)
                    pendingCameraUri = uri
                    cameraLauncher.launch(uri)
                } else {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }
        )
    }
}

@Composable
private fun ProfileEditContent(
    uiState: ProfileEditUiState,
    snackbarHostState: SnackbarHostState,
    onBack: () -> Unit,
    onNameChange: (String) -> Unit,
    onResumeUrlChange: (String) -> Unit,
    onAvatarClick: () -> Unit,
    onDoneClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is ProfileEditUiState.Loading -> LoadingState(modifier)
        is ProfileEditUiState.Success -> ProfileEditContentFilled(
            profile = uiState.profile,
            snackbarHostState = snackbarHostState,
            onBack = onBack,
            onNameChange = onNameChange,
            onResumeUrlChange = onResumeUrlChange,
            onAvatarClick = onAvatarClick,
            onDoneClick = onDoneClick,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileEditContentFilled(
    profile: ProfileEditUiModel,
    snackbarHostState: SnackbarHostState,
    onBack: () -> Unit,
    onNameChange: (String) -> Unit,
    onResumeUrlChange: (String) -> Unit,
    onAvatarClick: () -> Unit,
    onDoneClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Редактирование") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onDoneClick) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Готово"
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AvatarCircle(
                avatarPath = profile.avatarPath,
                hasAvatar = profile.hasAvatar,
                placeholder = profile.avatarPlaceholder,
                onClick = onAvatarClick
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = profile.fullName,
                onValueChange = onNameChange,
                label = { Text("Имя") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = profile.resumeUrl,
                onValueChange = onResumeUrlChange,
                label = { Text("Ссылка на резюме") },
                placeholder = { Text("https://example.com/resume.pdf") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun AvatarPickerDialog(
    onDismiss: () -> Unit,
    onGalleryClick: () -> Unit,
    onCameraClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Выбрать аватарку") },
        text = { Text("Откуда взять фото?") },
        confirmButton = {
            TextButton(onClick = onGalleryClick) { Text("Галерея") }
        },
        dismissButton = {
            TextButton(onClick = onCameraClick) { Text("Камера") }
        }
    )
}

private fun createTempImageUri(context: android.content.Context): Uri {
    val file = File(context.cacheDir, "camera_${System.currentTimeMillis()}.jpg")
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )
}


@Preview(showBackground = true)
@Composable
private fun ProfileEditContentEmptyPreview() {
    GhibliCollectionTheme {
        ProfileEditContentFilled(
            profile = ProfileEditUiModel(
                fullName = "",
                avatarPath = "",
                hasAvatar = false,
                avatarPlaceholder = "?",
                resumeUrl = ""
            ),
            snackbarHostState = SnackbarHostState(),
            onBack = {},
            onNameChange = {},
            onResumeUrlChange = {},
            onAvatarClick = {},
            onDoneClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileEditContentFilledPreview() {
    GhibliCollectionTheme {
        ProfileEditContentFilled(
            profile = ProfileEditUiModel(
                fullName = "Иван Иванов",
                avatarPath = "",
                hasAvatar = false,
                avatarPlaceholder = "И",
                resumeUrl = "https://example.com/resume.pdf"
            ),
            snackbarHostState = SnackbarHostState(),
            onBack = {},
            onNameChange = {},
            onResumeUrlChange = {},
            onAvatarClick = {},
            onDoneClick = {}
        )
    }
}