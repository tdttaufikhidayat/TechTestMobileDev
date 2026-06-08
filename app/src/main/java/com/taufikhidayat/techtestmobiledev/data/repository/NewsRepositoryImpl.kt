package com.taufikhidayat.techtestmobiledev.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.taufikhidayat.techtestmobiledev.data.local.NewsDatabase
import com.taufikhidayat.techtestmobiledev.data.paging.NewsRemoteMediator
import com.taufikhidayat.techtestmobiledev.data.remote.NewsApi
import com.taufikhidayat.techtestmobiledev.data.remote.dto.ArticleDto
import com.taufikhidayat.techtestmobiledev.data.remote.dto.SourceDto
import com.taufikhidayat.techtestmobiledev.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi,
    private val db: NewsDatabase
) : NewsRepository {

    override suspend fun getSources(category: String): List<SourceDto> {
        val response = api.getSources(category)
        if (response.status == "ok") {
            return response.sources ?: emptyList()
        } else {
            throw Exception("Gagal mengambil data sumber berita")
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getArticles(sourceId: String, query: String?): Flow<PagingData<ArticleDto>> {
        val pagingSourceFactory = { db.articleDao().getArticlesBySource(sourceId) }

        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            remoteMediator = NewsRemoteMediator(api, db, sourceId, query),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            // Mapper: Ubah Entity kembali ke DTO agar UI tidak rusak
            pagingData.map { entity ->
                ArticleDto(
                    source = SourceDto(id = entity.sourceId, name = entity.sourceName, description = null, category = null),
                    author = entity.author,
                    title = entity.title,
                    description = entity.description,
                    url = entity.url,
                    urlToImage = entity.urlToImage,
                    publishedAt = entity.publishedAt,
                    content = entity.content
                )
            }
        }
    }
}