package com.taufikhidayat.techtestmobiledev.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taufikhidayat.techtestmobiledev.data.remote.dto.SourceDto
import com.taufikhidayat.techtestmobiledev.domain.repository.NewsRepository
import com.taufikhidayat.techtestmobiledev.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SourceViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    // Kategori default: general
    private val _selectedCategory = MutableStateFlow("general")
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _sourcesState = MutableStateFlow<UiState<List<SourceDto>>>(UiState.Loading)
    val sourcesState = _sourcesState.asStateFlow()

    init {
        viewModelScope.launch {
            // Menggabungkan perubahan kategori ATAU pencarian
            combine(_selectedCategory, _searchQuery) { category, query ->
                Pair(category, query)
            }
                .debounce(500L) // Jeda 0.5 detik sebelum menembak API (mencegah spam)
                .collect { (category, query) ->
                    fetchSources(category, query)
                }
        }
    }

    fun setCategory(category: String) {
        _selectedCategory.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun retry() {
        viewModelScope.launch {
            fetchSources(_selectedCategory.value, _searchQuery.value)
        }
    }

    private suspend fun fetchSources(category: String, query: String) {
        _sourcesState.value = UiState.Loading
        try {
            val sources = repository.getSources(category)
            val filteredSources = if (query.isNotBlank()) {
                sources.filter { it.name?.contains(query, ignoreCase = true) == true }
            } else {
                sources
            }

            if (filteredSources.isEmpty()) {
                _sourcesState.value = UiState.Error("Tidak ada sumber berita yang ditemukan.")
            } else {
                _sourcesState.value = UiState.Success(filteredSources)
            }
        } catch (e: Exception) {
            _sourcesState.value = UiState.Error(e.localizedMessage ?: "Terjadi kesalahan jaringan.")
        }
    }
}