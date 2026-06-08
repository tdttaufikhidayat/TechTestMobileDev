package com.taufikhidayat.techtestmobiledev

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.taufikhidayat.techtestmobiledev.presentation.navigation.Screen
import com.taufikhidayat.techtestmobiledev.presentation.ui.screen.ArticleScreen
import com.taufikhidayat.techtestmobiledev.presentation.ui.screen.DetailScreen
import com.taufikhidayat.techtestmobiledev.presentation.ui.screen.SourceScreen
import com.taufikhidayat.techtestmobiledev.ui.theme.TechTestMobileDevTheme // Sesuaikan dengan nama theme bawaan project-mu
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TechTestMobileDevTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Source.route
                    ) {
                        // 1. Route ke Source Screen (Layar Utama)
                        composable(route = Screen.Source.route) {
                            SourceScreen(
                                onNavigateToArticle = { sourceId, sourceName ->
                                    navController.navigate(Screen.Article.createRoute(sourceId, sourceName))
                                }
                            )
                        }

                        // 2. Route ke Article Screen (Menerima argumen sourceId & sourceName)
                        composable(
                            route = Screen.Article.route,
                            arguments = listOf(
                                navArgument("sourceId") { type = NavType.StringType },
                                navArgument("sourceName") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val sourceId = backStackEntry.arguments?.getString("sourceId") ?: ""
                            val sourceName = backStackEntry.arguments?.getString("sourceName") ?: ""

                            ArticleScreen(
                                sourceId = sourceId,
                                sourceName = sourceName,
                                onNavigateToDetail = { url ->
                                    navController.navigate(Screen.Detail.createRoute(url))
                                },
                                onNavigateBack = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        // 3. Route ke Detail Screen (Menerima argumen URL Web)
                        composable(
                            route = Screen.Detail.route,
                            arguments = listOf(
                                navArgument("url") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val encodedUrl = backStackEntry.arguments?.getString("url") ?: ""
                            // Decode kembali URL-nya agar bisa dibaca WebView
                            val url = URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8.toString())

                            DetailScreen(
                                url = url,
                                onNavigateBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}