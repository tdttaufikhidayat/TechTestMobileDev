package com.taufikhidayat.techtestmobiledev.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Category.route
    ) {

        composable(Screen.Category.route) {
            // CategoryScreen(navController)
        }

        composable(
            route = Screen.Sources.route,
            arguments = listOf(
                navArgument("category") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val category = backStackEntry.arguments?.getString("category") ?: ""

            // SourcesScreen(navController, category)
        }

        composable(
            route = Screen.Articles.route,
            arguments = listOf(
                navArgument("sourceId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val sourceId = backStackEntry.arguments?.getString("sourceId") ?: ""

            // ArticlesScreen(navController, sourceId)
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("url") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->

            val url = backStackEntry.arguments?.getString("url") ?: ""

            // DetailScreen(url)
        }
    }
}