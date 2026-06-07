package com.taufikhidayat.techtestmobiledev.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taufikhidayat.techtestmobiledev.core.result.Result
import com.taufikhidayat.techtestmobiledev.data.repository.NewsRepository
import com.taufikhidayat.techtestmobiledev.ui.state.NewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState

    fun getSources(category: String) {
        viewModelScope.launch {

            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = repository.getSources(category)) {

                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            sources = result.data
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }

                is Result.Loading -> Unit
            }
        }
    }

    fun getArticles(source: String) {
        viewModelScope.launch {

            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = repository.getArticles(source)) {

                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            articles = result.data
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }

                is Result.Loading -> Unit
            }
        }
    }
}