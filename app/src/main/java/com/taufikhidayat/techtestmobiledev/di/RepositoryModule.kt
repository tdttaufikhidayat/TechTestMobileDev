package com.taufikhidayat.techtestmobiledev.di

import com.taufikhidayat.techtestmobiledev.data.remote.api.NewsApiService
import com.taufikhidayat.techtestmobiledev.data.repository.NewsRepositoryImpl
import com.taufikhidayat.techtestmobiledev.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNewsRepository(
        api: NewsApiService,
        apiKey: String
    ): NewsRepository {
        return NewsRepositoryImpl(api, apiKey)
    }
}