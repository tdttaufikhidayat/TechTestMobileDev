package com.taufikhidayat.techtestmobiledev.core.navigation

sealed class Screen(val route: String) {

    data object Category : Screen("category")

    data object Sources : Screen("sources/{category}") {
        fun createRoute(category: String) = "sources/$category"
    }

    data object Articles : Screen("articles/{sourceId}") {
        fun createRoute(sourceId: String) = "articles/$sourceId"
    }

    data object Detail : Screen("detail?url={url}") {
        fun createRoute(url: String) = "detail?url=$url"
    }
}