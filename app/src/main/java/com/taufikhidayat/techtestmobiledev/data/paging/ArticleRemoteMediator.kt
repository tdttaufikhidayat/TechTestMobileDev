package com.taufikhidayat.techtestmobiledev.data.paging

import androidx.paging.*
import com.taufikhidayat.techtestmobiledev.data.local.database.AppDatabase
import com.taufikhidayat.techtestmobiledev.data.local.entity.ArticleEntity
import com.taufikhidayat.techtestmobiledev.data.remote.api.NewsApiService

@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator(
    private val sourceId: String,
    private val api: NewsApiService,
    private val db: AppDatabase
) : RemoteMediator<Int, ArticleEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {

        return try {

            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            }

            val response = api.getArticles(sourceId)

            val articles = response.articles.map {
                ArticleEntity(
                    id = it.url ?: it.title,
                    title = it.title,
                    description = it.description,
                    imageUrl = it.urlToImage,
                    sourceId = sourceId
                )
            }

            db.articleDao().clearBySource(sourceId)
            db.articleDao().insertAll(articles)

            MediatorResult.Success(endOfPaginationReached = true)

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}