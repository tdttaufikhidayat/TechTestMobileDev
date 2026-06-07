package com.taufikhidayat.techtestmobiledev.di

import com.taufikhidayat.techtestmobiledev.core.network.RetrofitProvider
import com.taufikhidayat.techtestmobiledev.data.remote.api.NewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return RetrofitProvider.create()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }
}