package com.example.fittrackkk.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CompassCalibration
//import androidx.compose.material.icons.filled.DiscoverBright
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.fittrackkk.viewmodel.*

import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Info

@Composable
fun DashboardScreen(
    navController: NavHostController,
    dietViewModel: DietViewModel,
    exerciseViewModel: ExerciseViewModel,
    discoverViewModel: DiscoverViewModel,
    settingsViewModel: SettingsViewModel,
    authViewModel: AuthViewModel
) {
    val settingsState by settingsViewModel.uiState.collectAsState()
    val selectedTab = settingsState.selectedTab

    val tabs = listOf(
        TabItem("Diet", Icons.Default.Restaurant),
        TabItem("Plan", Icons.Default.FitnessCenter),
        TabItem("Recipes", Icons.Default.MenuBook),
        TabItem("Health", Icons.Default.Info),
        TabItem("Settings", Icons.Default.Settings)
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                tabs.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { settingsViewModel.setSelectedTab(index) },
                        icon = { Icon(tab.icon, contentDescription = tab.title) },
                        label = { Text(tab.title) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> DietScreen(
                    viewModel = dietViewModel,
                    onNavigateToDetail = { dayNumber ->
                        navController.navigate("day_detail/$dayNumber")
                    },
                    onNavigateToEdit = { dayNumber ->
                        navController.navigate("edit_plan/$dayNumber")
                    },
                    onNavigateToCustom = {
                        navController.navigate("my_meals")
                    }
                )
                1 -> PlanScreen(
                    viewModel = exerciseViewModel,
                    onNavigateToSession = { dayNumber ->
                        navController.navigate("exercise_session/$dayNumber")
                    },
                    onNavigateToEdit = { dayNumber ->
                        navController.navigate("edit_plan/$dayNumber")
                    },
                    onNavigateToCustom = {
                        navController.navigate("my_exercises")
                    }
                )
                2 -> DiscoverScreen(
                    viewModel = discoverViewModel,
                    initialTab = 0,
                    onNavigateToRecipe = { recipeId ->
                        navController.navigate("recipe_detail/$recipeId")
                    },
                    onNavigateToArticle = { articleId ->
                        navController.navigate("article_detail/$articleId")
                    }
                )
                3 -> DiscoverScreen(
                    viewModel = discoverViewModel,
                    initialTab = 1,
                    onNavigateToRecipe = { recipeId ->
                        navController.navigate("recipe_detail/$recipeId")
                    },
                    onNavigateToArticle = { articleId ->
                        navController.navigate("article_detail/$articleId")
                    }
                )
                4 -> SettingsScreen(
                    viewModel = settingsViewModel,
                    authViewModel = authViewModel,
                    onLoggedOut = {
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

data class TabItem(val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)
