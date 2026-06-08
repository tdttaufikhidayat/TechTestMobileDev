package com.taufikhidayat.techtestmobiledev.presentation.navigation

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class Screen(val route: String) {
    object Source : Screen("source_screen")

    // Melempar ID dan Nama Sumber ke halaman artikel
    object Article : Screen("article_screen/{sourceId}/{sourceName}") {
        fun createRoute(
            sourceId: String,
            sourceName: String,
        ): String {
            return "article_screen/$sourceId/$sourceName"
        }
    }

    // Melempar URL web ke halaman detail (URL harus di-encode agar tidak merusak format route)
    object Detail : Screen("detail_screen/{url}") {
        fun createRoute(url: String): String {
            val encodedUrl = URLEncoder.encode(url, StandardCharsets.UTF_8.toString())
            return "detail_screen/$encodedUrl"
        }
    }
}
