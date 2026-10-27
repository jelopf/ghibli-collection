package com.practicum.ghiblicollection.data.local.proto

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.practicum.ghiblicollection.domain.model.UserProfile
import com.practicum.ghiblicollection.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.File
import javax.inject.Inject

private val Context.profileDataStore: DataStore<UserProfileProto> by dataStore(
    fileName = "user_profile.pb",
    serializer = UserProfileSerializer
)

class ProfileDataStore @Inject constructor(
    private val context: Context
) : ProfileRepository {

    override val profile: Flow<UserProfile> = context.profileDataStore.data.map { proto ->
        UserProfile(
            fullName = proto.fullName,
            avatarPath = proto.avatarPath,
            resumeUrl = proto.resumeUrl
        )
    }

    override suspend fun saveProfile(profile: UserProfile) {
        context.profileDataStore.updateData { current ->
            current.toBuilder()
                .setFullName(profile.fullName)
                .setAvatarPath(profile.avatarPath)
                .setResumeUrl(profile.resumeUrl)
                .build()
        }
    }

    override suspend fun saveAvatar(sourceUri: String): String {
        val avatarsDir = File(context.filesDir, "avatars").apply { mkdirs() }
        val destFile = File(avatarsDir, "avatar_${System.currentTimeMillis()}.jpg")

        context.contentResolver.openInputStream(android.net.Uri.parse(sourceUri))?.use { input ->
            destFile.outputStream().use { output ->
                input.copyTo(output)
            }
        } ?: throw IllegalStateException("Не удалось открыть файл")

        context.profileDataStore.updateData { current ->
            val oldPath = current.avatarPath
            if (oldPath.isNotBlank()) {
                File(oldPath).delete()
            }
            current
        }

        return destFile.absolutePath
    }

    override suspend fun downloadResume(url: String): Long {
        val request = android.app.DownloadManager.Request(android.net.Uri.parse(url)).apply {
            setTitle("Резюме")
            setDescription("Скачивание резюме")
            setNotificationVisibility(
                android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
            )
            setDestinationInExternalPublicDir(
                android.os.Environment.DIRECTORY_DOWNLOADS,
                "resume_${System.currentTimeMillis()}.pdf"
            )
        }
        val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as android.app.DownloadManager
        return dm.enqueue(request)
    }
}