package com.taufikhidayat.techtestmobiledev.di

import com.taufikhidayat.techtestmobiledev.BuildConfig
import com.taufikhidayat.techtestmobiledev.data.remote.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor =
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

        // Interceptor untuk otomatis menambahkan API Key di setiap request
        val apiKeyInterceptor =
            Interceptor { chain ->
                val originalRequest = chain.request()
                val originalHttpUrl = originalRequest.url

                val url =
                    originalHttpUrl.newBuilder()
                        .addQueryParameter("apiKey", BuildConfig.NEWS_API_KEY)
                        .build()

                val requestBuilder = originalRequest.newBuilder().url(url)
                chain.proceed(requestBuilder.build())
            }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApi(okHttpClient: OkHttpClient): NewsApi {
        return Retrofit.Builder()
            .baseUrl(NewsApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }
}
