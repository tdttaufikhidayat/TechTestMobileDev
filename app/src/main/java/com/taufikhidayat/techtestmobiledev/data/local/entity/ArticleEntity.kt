package com.taufikhidayat.techtestmobiledev.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String?,
    val imageUrl: String?,
    val sourceId: String
)