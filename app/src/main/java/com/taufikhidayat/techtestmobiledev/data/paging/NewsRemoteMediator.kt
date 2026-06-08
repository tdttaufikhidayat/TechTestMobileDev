package com.taufikhidayat.techtestmobiledev.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.taufikhidayat.techtestmobiledev.data.local.NewsDatabase
import com.taufikhidayat.techtestmobiledev.data.local.entity.ArticleEntity
import com.taufikhidayat.techtestmobiledev.data.local.entity.RemoteKeys
import com.taufikhidayat.techtestmobiledev.data.remote.NewsApi
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val api: NewsApi,
    private val db: NewsDatabase,
    private val sourceId: String,
    private val query: String? = null
) : RemoteMediator<Int, ArticleEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val response = api.getArticles(
                sources = sourceId,
                query = if (query.isNullOrBlank()) null else query,
                page = page,
                pageSize = state.config.pageSize
            )

            // Membuang artikel yang tidak punya URL atau judulnya di-remove
            val articlesDto = response.articles?.filter {
                it.url != null && it.title != "[Removed]"
            } ?: emptyList()

            val endOfPaginationReached = articlesDto.isEmpty()

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.remoteKeysDao().clearRemoteKeys()
                    db.articleDao().clearArticlesBySource(sourceId)
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val keys = articlesDto.map {
                    RemoteKeys(articleUrl = it.url!!, prevKey = prevKey, nextKey = nextKey)
                }

                val entities = articlesDto.map { dto ->
                    ArticleEntity(
                        url = dto.url!!,
                        sourceId = sourceId,
                        sourceName = dto.source?.name,
                        author = dto.author,
                        title = dto.title,
                        description = dto.description,
                        urlToImage = dto.urlToImage,
                        publishedAt = dto.publishedAt,
                        content = dto.content
                    )
                }

                db.remoteKeysDao().insertAll(keys)
                db.articleDao().insertAll(entities)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ArticleEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { article -> db.remoteKeysDao().remoteKeysArticleId(article.url) }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ArticleEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { article -> db.remoteKeysDao().remoteKeysArticleId(article.url) }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ArticleEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.url?.let { url ->
                db.remoteKeysDao().remoteKeysArticleId(url)
            }
        }
    }
}