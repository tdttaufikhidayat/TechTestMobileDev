package com.taufikhidayat.techtestmobiledev.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.taufikhidayat.techtestmobiledev.core.navigation.Screen
import com.taufikhidayat.techtestmobiledev.presentation.ui.ArticlesScreen
import com.taufikhidayat.techtestmobiledev.presentation.ui.CategoryScreen
import com.taufikhidayat.techtestmobiledev.presentation.ui.SourcesScreen
import com.taufikhidayat.techtestmobiledev.presentation.viewmodel.NewsViewModel

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Category.route
    ) {

        // ---------------- CATEGORY ----------------
        composable(route = Screen.Category.route) {
            CategoryScreen(
                onCategoryClick = { category ->
                    navController.navigate(
                        Screen.Sources.createRoute(category)
                    )
                }
            )
        }

        // ---------------- SOURCES ----------------
        composable(
            route = Screen.Sources.route,
            arguments = listOf(
                navArgument("category") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->

            val category = backStackEntry.arguments?.getString("category") ?: ""

            val viewModel: NewsViewModel = hiltViewModel()

            LaunchedEffect(category) {
                if (category.isNotEmpty()) {
                    viewModel.loadSources(category)
                }
            }

            SourcesScreen(
                viewModel = viewModel,
                onSourceClick = { sourceId ->
                    navController.navigate(
                        Screen.Articles.createRoute(sourceId)
                    )
                }
            )
        }

        // ---------------- ARTICLES ----------------
        composable(
            route = Screen.Articles.route,
            arguments = listOf(
                navArgument("source") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->

            val source = backStackEntry.arguments?.getString("source") ?: ""

            val viewModel: NewsViewModel = hiltViewModel()

            LaunchedEffect(source) {
                if (source.isNotEmpty()) {
                    viewModel.loadArticles(source)
                }
            }

            ArticlesScreen(
                viewModel = viewModel
            )
        }
    }
}