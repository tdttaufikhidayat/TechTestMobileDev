package com.taufikhidayat.techtestmobiledev.data.remote.dto

data class SourcesResponse(
    val status: String,
    val sources: List<SourceDto>
)

data class SourceDto(
    val id: String?,
    val name: String,
    val description: String?,
    val url: String?,
    val category: String?,
    val language: String?,
    val country: String?
)