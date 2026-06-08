package com.taufikhidayat.techtestmobiledev.core.navigation

sealed class Screen(val route: String) {

    data object Category : Screen("category")

    data object Sources : Screen("sources/{category}") {
        fun createRoute(category: String) = "sources/$category"
    }

    data object Articles : Screen("articles/{source}") {
        fun createRoute(source: String) = "articles/$source"
    }
}