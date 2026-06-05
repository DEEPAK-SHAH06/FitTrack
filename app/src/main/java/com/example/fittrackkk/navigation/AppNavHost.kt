package com.example.fittrackkk.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.fittrackkk.ui.screens.*
import com.example.fittrackkk.viewmodel.*

@Composable
fun AppNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel(),
    userInfoViewModel: UserInfoViewModel = viewModel(),
    dietViewModel: DietViewModel = viewModel(),
    exerciseViewModel: ExerciseViewModel = viewModel(),
    discoverViewModel: DiscoverViewModel = viewModel(),
    settingsViewModel: SettingsViewModel = viewModel()
) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToUserInfo = {
                    navController.navigate(Screen.UserInfo.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                authViewModel = authViewModel,
                settingsViewModel = settingsViewModel
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = authViewModel,
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onNavigateToUserInfo = {
                    navController.navigate(Screen.UserInfo.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                viewModel = authViewModel,
                onNavigateToLogin = { navController.popBackStack() },
                onNavigateToUserInfo = {
                    navController.navigate(Screen.UserInfo.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.UserInfo.route) {
            UserInfoScreen(
                viewModel = userInfoViewModel,
                onNavigateToDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.UserInfo.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen(
                navController = navController,
                dietViewModel = dietViewModel,
                exerciseViewModel = exerciseViewModel,
                discoverViewModel = discoverViewModel,
                settingsViewModel = settingsViewModel,
                authViewModel = authViewModel
            )
        }

        composable(
            route = Screen.DayDetail.route,
            arguments = listOf(navArgument("dayNumber") { type = NavType.IntType })
        ) { backStackEntry ->
            val dayNumber = backStackEntry.arguments?.getInt("dayNumber") ?: 1
            DayDetailScreen(
                dayNumber = dayNumber,
                viewModel = dietViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.ExerciseSession.route,
            arguments = listOf(navArgument("dayNumber") { type = NavType.IntType })
        ) { backStackEntry ->
            val dayNumber = backStackEntry.arguments?.getInt("dayNumber") ?: 1
            ExerciseSessionScreen(
                dayNumber = dayNumber,
                viewModel = exerciseViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.RecipeDetail.route,
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: 1
            RecipeDetailScreen(
                recipeId = recipeId,
                viewModel = discoverViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.ArticleDetail.route,
            arguments = listOf(navArgument("articleId") { type = NavType.IntType })
        ) { backStackEntry ->
            val articleId = backStackEntry.arguments?.getInt("articleId") ?: 1
            ArticleDetailScreen(
                articleId = articleId,
                viewModel = discoverViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
