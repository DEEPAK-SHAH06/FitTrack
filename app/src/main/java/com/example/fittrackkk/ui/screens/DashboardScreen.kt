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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.fittrackkk.viewmodel.*

@Composable
fun DashboardScreen(
    navController: NavHostController,
    dietViewModel: DietViewModel,
    exerciseViewModel: ExerciseViewModel,
    discoverViewModel: DiscoverViewModel,
    settingsViewModel: SettingsViewModel,
    authViewModel: AuthViewModel
) {
    var selectedTab by remember { mutableStateOf(0) }

    val tabs = listOf(
        TabItem("Diet", Icons.Default.Restaurant),
        TabItem("Plan", Icons.Default.FitnessCenter),
        TabItem("Discover", Icons.Default.Explore),
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
                        onClick = { selectedTab = index },
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
                    }
                )
                1 -> PlanScreen(
                    viewModel = exerciseViewModel,
                    onNavigateToSession = { dayNumber ->
                        navController.navigate("exercise_session/$dayNumber")
                    }
                )
                2 -> DiscoverScreen(
                    viewModel = discoverViewModel,
                    onNavigateToRecipe = { recipeId ->
                        navController.navigate("recipe_detail/$recipeId")
                    },
                    onNavigateToArticle = { articleId ->
                        navController.navigate("article_detail/$articleId")
                    }
                )
                3 -> SettingsScreen(
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
