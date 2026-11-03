package com.practicum.ghiblicollection.domain.repository

interface ReminderRepository {
    fun schedule(time: String, name: String)
    fun shouldRequestExactAlarmPermission(): Boolean
}