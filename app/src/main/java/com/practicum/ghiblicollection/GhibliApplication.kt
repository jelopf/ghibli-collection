package com.practicum.ghiblicollection

import android.app.Application
import com.practicum.ghiblicollection.data.notification.LessonNotificationChannel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GhibliApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        LessonNotificationChannel.create(this)
    }
}