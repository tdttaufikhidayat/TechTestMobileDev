package com.taufikhidayat.techtestmobiledev.domain.repository

import androidx.paging.PagingData
import com.taufikhidayat.techtestmobiledev.data.remote.dto.ArticleDto
import com.taufikhidayat.techtestmobiledev.data.remote.dto.SourceDto
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getSources(category: String): List<SourceDto>
    fun getArticles(sourceId: String, query: String? = null): Flow<PagingData<ArticleDto>>
}