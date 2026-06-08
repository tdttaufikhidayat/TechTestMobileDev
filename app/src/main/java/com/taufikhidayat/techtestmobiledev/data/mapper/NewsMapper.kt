package com.taufikhidayat.techtestmobiledev.data.mapper

import com.taufikhidayat.techtestmobiledev.data.remote.dto.ArticleDto
import com.taufikhidayat.techtestmobiledev.data.remote.dto.SourceDto
import com.taufikhidayat.techtestmobiledev.domain.model.Article
import com.taufikhidayat.techtestmobiledev.domain.model.Source

fun SourceDto.toDomain(): Source {
    return Source(
        id = id ?: "",
        name = name,
        description = description ?: ""
    )
}

fun ArticleDto.toDomain(): Article {
    return Article(
        title = title ?: "",
        description = description ?: "",
        url = url,
        imageUrl = urlToImage,
        sourceName = source?.name ?: ""
    )
}