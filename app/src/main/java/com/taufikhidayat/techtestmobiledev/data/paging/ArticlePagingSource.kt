package com.taufikhidayat.techtestmobiledev.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.taufikhidayat.techtestmobiledev.data.remote.NewsApi
import com.taufikhidayat.techtestmobiledev.data.remote.dto.ArticleDto
import retrofit2.HttpException
import java.io.IOException

class ArticlePagingSource(
    private val api: NewsApi,
    private val sourceId: String,
    private val query: String? = null,
) : PagingSource<Int, ArticleDto>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleDto> {
        val currentPage = params.key ?: 1
        return try {
            val response =
                api.getArticles(
                    sources = sourceId,
                    query = if (query.isNullOrBlank()) null else query,
                    page = currentPage,
                    pageSize = params.loadSize,
                )

            val articles = response.articles?.filter { it.title != "[Removed]" } ?: emptyList()

            LoadResult.Page(
                data = articles,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (articles.isEmpty()) null else currentPage + 1,
            )
        } catch (e: IOException) {
            LoadResult.Error(e) // Error jaringan (tidak ada internet)
        } catch (e: HttpException) {
            LoadResult.Error(e) // Error dari server (misal limit API habis)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
