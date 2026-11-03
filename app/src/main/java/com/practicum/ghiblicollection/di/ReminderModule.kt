package com.practicum.ghiblicollection.di

import com.practicum.ghiblicollection.data.reminder.LessonReminderScheduler
import com.practicum.ghiblicollection.domain.repository.ReminderRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ReminderModule {

    @Binds
    @Singleton
    abstract fun bindReminderRepository(
        impl: LessonReminderScheduler
    ): ReminderRepository
}