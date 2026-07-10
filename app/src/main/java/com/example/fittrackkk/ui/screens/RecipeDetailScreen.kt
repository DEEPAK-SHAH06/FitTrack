package com.example.fittrackkk.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Flatware
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.fittrackkk.data.model.Recipe
import com.example.fittrackkk.ui.theme.FitTrackkkTheme
import com.example.fittrackkk.ui.theme.GradientStart
import com.example.fittrackkk.ui.theme.TextSecondaryLight
import com.example.fittrackkk.ui.theme.WarningOrange
import com.example.fittrackkk.viewmodel.DiscoverViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipeId: Int,
    viewModel: DiscoverViewModel,
    onBack: () -> Unit,
    onEditRecipe: (Int) -> Unit
) {
    val selectedRecipe by viewModel.selectedRecipe.collectAsState()

    LaunchedEffect(recipeId) {
        viewModel.loadRecipe(recipeId)
    }

    RecipeDetailScreenContent(
        recipe = selectedRecipe,
        onBack = onBack,
        onEditRecipe = onEditRecipe,
        onDeleteRecipe = { recipe ->
            viewModel.deleteRecipe(recipe)
            onBack()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreenContent(
    recipe: Recipe?,
    onBack: () -> Unit,
    onEditRecipe: (Int) -> Unit,
    onDeleteRecipe: (Recipe) -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(recipe?.title ?: "Recipe", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (recipe?.isUserCreated == true) {
                        IconButton(onClick = { onEditRecipe(recipe.id) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit Recipe")
                        }
                        IconButton(onClick = { onDeleteRecipe(recipe) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete Recipe")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {
            recipe?.let { r ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(16.dp)
                ) {
                    // Coil AsyncImage header if image URL exists
                    if (r.imageUrl.isNotBlank()) {
                        AsyncImage(
                            model = r.imageUrl,
                            contentDescription = r.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .padding(bottom = 16.dp)
                        )
                    }

                    // User Created Edit/Delete Quick Actions
                    if (r.isUserCreated) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // In AppNavHost, navigation to edit screen can be triggered from Parent/ViewModel.
                            // But here we can show buttons inline as well:
                            // We can use discoverViewModel if present to fetch navigation indirectly, 
                            // but standard callback is cleaner.
                        }
                    }

                    // Quick Specs
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Card(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = WarningOrange)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Calories", style = MaterialTheme.typography.bodySmall, color = TextSecondaryLight)
                                Text("${r.calories} kcal", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                            }
                        }
                        Card(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(Icons.Default.Timer, contentDescription = null, tint = GradientStart)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Prep Time", style = MaterialTheme.typography.bodySmall, color = TextSecondaryLight)
                                Text("${r.prepTimeMinutes} mins", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    // Ingredients Section
                    Text("Ingredients", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            r.ingredients.split("\n").filter { it.isNotBlank() }.forEach { ingredient ->
                                Row(
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.Flatware,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp),
                                        tint = MaterialTheme.colorScheme.secondary
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(ingredient, style = MaterialTheme.typography.bodyLarge)
                                }
                            }
                        }
                    }

                    // Steps Section
                    Text("Cooking Steps", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 40.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            r.steps.split("\n").filter { it.isNotBlank() }.forEachIndexed { index, step ->
                                Row(
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .background(
                                                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                                shape = RoundedCornerShape(12.dp)
                                              ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            "${index + 1}",
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.Bold,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(step, style = MaterialTheme.typography.bodyLarge)
                                }
                            }
                        }
                    }
                }
            } ?: Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailScreenPreview() {
    FitTrackkkTheme {
        RecipeDetailScreenContent(
            recipe = Recipe(
                id = 1,
                title = "Authentic Dal Bhat",
                category = "Lunch",
                ingredients = "1 cup rice\n1/2 cup lentils",
                steps = "1. Cook rice\n2. Cook lentils",
                calories = 650,
                prepTimeMinutes = 20,
                imageUrl = "https://example.com/dalbhat.jpg",
                isUserCreated = true
            ),
            onBack = {},
            onEditRecipe = {},
            onDeleteRecipe = {}
        )
    }
}
