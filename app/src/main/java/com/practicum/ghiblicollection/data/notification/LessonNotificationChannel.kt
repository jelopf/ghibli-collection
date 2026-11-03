package com.practicum.ghiblicollection.data.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

object LessonNotificationChannel {
    const val ID = "lesson_reminder"

    fun create(context: Context) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            ID,
            "Напоминание о паре",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            enableVibration(true)
            setShowBadge(true)
        }
        manager.createNotificationChannel(channel)
    }
}