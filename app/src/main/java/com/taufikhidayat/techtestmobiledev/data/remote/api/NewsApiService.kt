package com.taufikhidayat.techtestmobiledev.data.remote.api

import com.taufikhidayat.techtestmobiledev.data.remote.dto.ArticlesResponse
import com.taufikhidayat.techtestmobiledev.data.remote.dto.SourcesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines/sources")
    suspend fun getSources(
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): SourcesResponse

    @GET("top-headlines")
    suspend fun getArticles(
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String
    ): ArticlesResponse

    @GET("everything")
    suspend fun searchArticles(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): ArticlesResponse
}