package com.taufikhidayat.techtestmobiledev.di

import com.taufikhidayat.techtestmobiledev.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiKeyModule {

    @Provides
    @Singleton
    fun provideApiKey(): String {
        return BuildConfig.NEWS_API_KEY
    }
}