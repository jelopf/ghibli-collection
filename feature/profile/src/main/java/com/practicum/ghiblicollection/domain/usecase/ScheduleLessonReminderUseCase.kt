package com.practicum.ghiblicollection.domain.usecase

import com.practicum.ghiblicollection.domain.repository.ReminderRepository
import javax.inject.Inject

class ScheduleLessonReminderUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository
) {
    operator fun invoke(time: String, name: String) {
        reminderRepository.schedule(time, name)
    }
}