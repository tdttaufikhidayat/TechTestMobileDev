package com.taufikhidayat.techtestmobiledev.data.remote

import com.taufikhidayat.techtestmobiledev.data.remote.dto.NewsResponse
import com.taufikhidayat.techtestmobiledev.data.remote.dto.SourceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines/sources")
    suspend fun getSources(
        @Query("category") category: String
    ): SourceResponse

    @GET("everything")
    suspend fun getArticles(
        @Query("sources") sources: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): NewsResponse

    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
    }
}