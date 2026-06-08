package com.taufikhidayat.techtestmobiledev.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.taufikhidayat.techtestmobiledev.data.remote.dto.ArticleDto
import com.taufikhidayat.techtestmobiledev.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class ArticleViewModel
    @Inject
    constructor(
        private val repository: NewsRepository,
    ) : ViewModel() {
        private val _searchQuery = MutableStateFlow("")
        val searchQuery = _searchQuery.asStateFlow()

        fun setSearchQuery(query: String) {
            _searchQuery.value = query
        }

        // flatMapLatest memastikan jika user mengetik pencarian baru, API lama dibatalkan dan memanggil yang baru
        fun getArticles(sourceId: String): Flow<PagingData<ArticleDto>> {
            return _searchQuery
                .debounce(500L)
                .flatMapLatest { query ->
                    repository.getArticles(sourceId, query)
                }
                .cachedIn(viewModelScope) // Wajib ada agar Paging tidak crash saat rotasi layar
        }
    }
