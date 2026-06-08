package com.taufikhidayat.techtestmobiledev.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.taufikhidayat.techtestmobiledev.data.paging.ArticlePagingSource
import com.taufikhidayat.techtestmobiledev.data.remote.NewsApi
import com.taufikhidayat.techtestmobiledev.data.remote.dto.ArticleDto
import com.taufikhidayat.techtestmobiledev.data.remote.dto.SourceDto
import com.taufikhidayat.techtestmobiledev.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi
) : NewsRepository {

    override suspend fun getSources(category: String): List<SourceDto> {
        val response = api.getSources(category)
        if (response.status == "ok") {
            return response.sources ?: emptyList()
        } else {
            throw Exception("Gagal mengambil data sumber berita")
        }
    }

    override fun getArticles(sourceId: String, query: String?): Flow<PagingData<ArticleDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ArticlePagingSource(api, sourceId, query) }
        ).flow
    }
}