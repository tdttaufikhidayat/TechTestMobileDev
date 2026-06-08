package com.taufikhidayat.techtestmobiledev.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.taufikhidayat.techtestmobiledev.core.navigation.Screen
import com.taufikhidayat.techtestmobiledev.presentation.viewmodel.NewsViewModel
import com.taufikhidayat.techtestmobiledev.presentation.ui.CategoryScreen
import com.taufikhidayat.techtestmobiledev.presentation.ui.SourcesScreen
import com.taufikhidayat.techtestmobiledev.presentation.ui.ArticlesScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Category.route
    ) {

        composable(Screen.Category.route) {
            CategoryScreen(
                onCategoryClick = { category ->
                    navController.navigate(Screen.Sources.createRoute(category))
                }
            )
        }

        composable(Screen.Sources.route) { backStackEntry ->
            val vm: NewsViewModel = hiltViewModel()

            SourcesScreen(
                viewModel = vm,
                onSourceClick = { source ->
                    navController.navigate(Screen.Articles.createRoute(source))
                }
            )
        }

        composable(Screen.Articles.route) {
            val vm: NewsViewModel = hiltViewModel()

            ArticlesScreen(viewModel = vm)
        }
    }
}