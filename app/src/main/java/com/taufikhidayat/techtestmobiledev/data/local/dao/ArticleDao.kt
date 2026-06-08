package com.taufikhidayat.techtestmobiledev.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.taufikhidayat.techtestmobiledev.data.local.entity.ArticleEntity

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles WHERE sourceId = :sourceId")
    fun getArticles(sourceId: String): PagingSource<Int, ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<ArticleEntity>)

    @Query("DELETE FROM articles WHERE sourceId = :sourceId")
    suspend fun clearBySource(sourceId: String)
}