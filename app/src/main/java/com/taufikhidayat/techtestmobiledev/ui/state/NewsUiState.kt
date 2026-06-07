package com.taufikhidayat.techtestmobiledev.ui.state

import com.taufikhidayat.techtestmobiledev.data.remote.dto.SourcesResponse
import com.taufikhidayat.techtestmobiledev.data.remote.dto.ArticlesResponse

data class NewsUiState(
    val isLoading: Boolean = false,
    val error: String? = null,

    val sources: SourcesResponse? = null,
    val articles: ArticlesResponse? = null
)