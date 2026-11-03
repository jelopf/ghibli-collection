package com.practicum.ghiblicollection.di

import android.content.Context
import com.practicum.ghiblicollection.data.local.proto.ProfileDataStore
import com.practicum.ghiblicollection.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileModule {

    @Binds
    @Singleton
    abstract fun bindProfileRepository(impl: ProfileDataStore): ProfileRepository

    companion object {
        @Provides
        @Singleton
        fun provideProfileDataStore(@ApplicationContext context: Context): ProfileDataStore =
            ProfileDataStore(context)
    }
}