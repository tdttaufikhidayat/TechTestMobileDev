package com.taufikhidayat.techtestmobiledev.data.repository

import com.taufikhidayat.techtestmobiledev.core.result.Result
import com.taufikhidayat.techtestmobiledev.data.mapper.toDomain
import com.taufikhidayat.techtestmobiledev.data.remote.api.NewsApiService
import com.taufikhidayat.techtestmobiledev.domain.model.Article
import com.taufikhidayat.techtestmobiledev.domain.model.Source
import com.taufikhidayat.techtestmobiledev.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApiService,
    private val apiKey: String
) : NewsRepository {

    override suspend fun getSources(category: String): Result<List<Source>> {
        return safeApiCall {
            api.getSources(category, apiKey)
                .sources
                .map { it.toDomain() }
        }
    }

    override suspend fun getArticles(source: String): Result<List<Article>> {
        return safeApiCall {
            api.getArticles(source, apiKey)
                .articles
                .map { it.toDomain() }
        }
    }

    override suspend fun searchArticles(query: String): Result<List<Article>> {
        return safeApiCall {
            api.searchArticles(query, apiKey)
                .articles
                .map { it.toDomain() }
        }
    }

    private inline fun <T> safeApiCall(call: () -> T): Result<T> {
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