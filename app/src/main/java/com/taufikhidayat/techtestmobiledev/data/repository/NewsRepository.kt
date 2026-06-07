package com.taufikhidayat.techtestmobiledev.data.repository

import com.taufikhidayat.techtestmobiledev.BuildConfig
import com.taufikhidayat.techtestmobiledev.core.result.Result
import com.taufikhidayat.techtestmobiledev.data.remote.api.NewsApiService
import com.taufikhidayat.techtestmobiledev.data.remote.dto.ArticlesResponse
import com.taufikhidayat.techtestmobiledev.data.remote.dto.SourcesResponse
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val api: NewsApiService
) {

    suspend fun getSources(category: String): Result<SourcesResponse> {
        return safeApiCall {
            api.getSources(category, BuildConfig.NEWS_API_KEY)
        }
    }

    suspend fun getArticles(source: String): Result<ArticlesResponse> {
        return safeApiCall {
            api.getArticles(source, BuildConfig.NEWS_API_KEY)
        }
    }

    suspend fun searchArticles(query: String): Result<ArticlesResponse> {
        return safeApiCall {
            api.searchArticles(query, BuildConfig.NEWS_API_KEY)
        }
    }

    private inline fun <T> safeApiCall(
        call: () -> T
    ): Result<T> {
        return try {
            Result.Success(call())
        } catch (e: Exception) {
            Result.Error(
                message = e.message ?: "Unknown error",
                throwable = e
            )
        }
    }
}