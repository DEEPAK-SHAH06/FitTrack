package com.example.fittrackkk.ui.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fittrackkk.ui.theme.FitTrackkkTheme
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
    val settingsState by settingsViewModel.uiState.collectAsState()
    val selectedTab = settingsState.selectedTab
    val context = LocalContext.current

    var showExitDialog by remember { mutableStateOf(false) }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Exit Application", fontWeight = FontWeight.Bold) },
            text = { Text("Do you really want to quit?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showExitDialog = false
                        (context as? Activity)?.finish()
                    }
                ) {
                    Text("Confirm", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    BackHandler {
        showExitDialog = true
    }

    DashboardScreenContent(
        selectedTab = selectedTab,
        onTabSelected = { settingsViewModel.setSelectedTab(it) },
        dietScreen = {
            DietScreen(
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
        },
        planScreen = {
            PlanScreen(
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
        },
        recipesScreen = {
            DiscoverScreen(
                viewModel = discoverViewModel,
                initialTab = 0,
                onNavigateToRecipe = { recipeId ->
                    navController.navigate("recipe_detail/$recipeId")
                },
                onNavigateToArticle = { articleId ->
                    navController.navigate("article_detail/$articleId")
                },
                onAddRecipeClick = {
                    navController.navigate("add_recipe")
                }
            )
        },
        settingsScreen = {
            SettingsScreen(
                viewModel = settingsViewModel,
                authViewModel = authViewModel,
                onLoggedOut = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    )
}

data class TabItem(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
fun DashboardScreenContent(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    dietScreen: @Composable () -> Unit,
    planScreen: @Composable () -> Unit,
    recipesScreen: @Composable () -> Unit,
    settingsScreen: @Composable () -> Unit
) {
    val tabs = listOf(
        TabItem("Diet", Icons.Default.Restaurant),
        TabItem("Plan", Icons.Default.FitnessCenter),
        TabItem("Recipes", Icons.Default.MenuBook),
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
                        onClick = { onTabSelected(index) },
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
                0 -> dietScreen()
                1 -> planScreen()
                2 -> recipesScreen()
                3 -> settingsScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    FitTrackkkTheme {
        DashboardScreenContent(
            selectedTab = 0,
            onTabSelected = {},
            dietScreen = { Box(modifier = Modifier.fillMaxSize()) { Text("Diet Content") } },
            planScreen = { Box(modifier = Modifier.fillMaxSize()) { Text("Plan Content") } },
            recipesScreen = { Box(modifier = Modifier.fillMaxSize()) { Text("Recipes Content") } },
            settingsScreen = { Box(modifier = Modifier.fillMaxSize()) { Text("Settings Content") } }
        )
    }
}
