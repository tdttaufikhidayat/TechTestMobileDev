package com.taufikhidayat.techtestmobiledev.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines/sources")
    suspend fun getSources(
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): Any

    @GET("top-headlines")
    suspend fun getArticles(
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String
    ): Any

    @GET("everything")
    suspend fun searchArticles(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): Any
}