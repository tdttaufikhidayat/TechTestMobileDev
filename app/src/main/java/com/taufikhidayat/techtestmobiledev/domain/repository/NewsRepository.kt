package com.taufikhidayat.techtestmobiledev.domain.repository

import com.taufikhidayat.techtestmobiledev.core.result.Result
import com.taufikhidayat.techtestmobiledev.domain.model.Article
import com.taufikhidayat.techtestmobiledev.domain.model.Source

interface NewsRepository {

    suspend fun getSources(category: String): Result<List<Source>>

    suspend fun getArticles(source: String): Result<List<Article>>

    suspend fun searchArticles(query: String): Result<List<Article>>
}