package com.taufikhidayat.techtestmobiledev.data.local

import androidx.paging.PagingSource
import androidx.room.*
import com.taufikhidayat.techtestmobiledev.data.local.entity.ArticleEntity
import com.taufikhidayat.techtestmobiledev.data.local.entity.RemoteKeys

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<ArticleEntity>)

    // Return PagingSource langsung dari Room
    @Query("SELECT * FROM articles WHERE sourceId = :sourceId OR :sourceId = '' ORDER BY publishedAt DESC")
    fun getArticlesBySource(sourceId: String): PagingSource<Int, ArticleEntity>

    @Query("DELETE FROM articles WHERE sourceId = :sourceId")
    suspend fun clearArticlesBySource(sourceId: String)
}

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE articleUrl = :articleUrl")
    suspend fun remoteKeysArticleId(articleUrl: String): RemoteKeys?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}

@Database(entities = [ArticleEntity::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}