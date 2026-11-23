package com.practicum.ghiblicollection.di

import android.content.Context
import com.practicum.ghiblicollection.data.local.datastore.FilterDataStore
import com.practicum.ghiblicollection.domain.repository.FilterRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FilterModule {

    @Binds
    @Singleton
    abstract fun bindFilterRepository(
        impl: FilterDataStore
    ): FilterRepository

    companion object {
        @Provides
        @Singleton
        fun provideFilterDataStore(
            @ApplicationContext context: Context
        ): FilterDataStore = FilterDataStore(context)
    }
}