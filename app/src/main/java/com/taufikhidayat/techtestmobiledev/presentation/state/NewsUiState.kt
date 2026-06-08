package com.taufikhidayat.techtestmobiledev.presentation.state

import com.taufikhidayat.techtestmobiledev.domain.model.Article
import com.taufikhidayat.techtestmobiledev.domain.model.Source

data class NewsUiState(
    val isLoading: Boolean = false,
    val sources: List<Source> = emptyList(),
    val articles: List<Article> = emptyList(),
    val error: String? = null
)