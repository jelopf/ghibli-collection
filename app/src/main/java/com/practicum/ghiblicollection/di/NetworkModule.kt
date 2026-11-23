package com.practicum.ghiblicollection.di

import com.practicum.ghiblicollection.data.network.GhibliApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://ghibliapi.vercel.app/"

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .callTimeout(15, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideGhibliApi(retrofit: Retrofit): GhibliApi =
        retrofit.create(GhibliApi::class.java)
}