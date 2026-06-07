package com.taufikhidayat.techtestmobiledev.data.remote.dto

data class ArticlesResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleDto>
)

data class ArticleDto(
    val source: ArticleSourceDto?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)

data class ArticleSourceDto(
    val id: String?,
    val name: String?
)