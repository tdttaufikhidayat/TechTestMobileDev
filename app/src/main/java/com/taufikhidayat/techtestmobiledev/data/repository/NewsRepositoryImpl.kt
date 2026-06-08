package com.taufikhidayat.techtestmobiledev.data.repository

import androidx.paging.*
import com.taufikhidayat.techtestmobiledev.data.local.database.AppDatabase
import com.taufikhidayat.techtestmobiledev.data.paging.ArticleRemoteMediator
import com.taufikhidayat.techtestmobiledev.data.remote.api.NewsApiService
import com.taufikhidayat.techtestmobiledev.domain.model.Article
import com.taufikhidayat.techtestmobiledev.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl(
    private val api: NewsApiService,
    private val db: AppDatabase
) : NewsRepository {

    override fun getArticles(sourceId: String): Flow<PagingData<Article>> {

        val pagingSourceFactory = {
            db.articleDao().getArticles(sourceId)
        }

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = ArticleRemoteMediator(
                sourceId = sourceId,
                api = api,
                db = db
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map {
                Article(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    imageUrl = it.imageUrl,
                    sourceId = it.sourceId
                )
            }
        }
    }
}