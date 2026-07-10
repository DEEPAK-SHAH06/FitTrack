package com.example.fittrackkk.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fittrackkk.data.model.CustomMeal
import com.example.fittrackkk.viewmodel.DietViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyMealsScreen(
    viewModel: DietViewModel,
    onNavigateBack: () -> Unit
) {
    val meals by viewModel.customMeals.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var mealToEdit by remember { mutableStateOf<CustomMeal?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Custom Meals") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Meal")
            }
        }
    ) { padding ->
        if (meals.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("No custom meals yet. Add some!")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(meals, key = { it.id }) { meal ->
                    MealItem(
                        meal = meal,
                        onEdit = { mealToEdit = meal },
                        onDelete = { viewModel.deleteCustomMeal(meal) }
                    )
                }
            }
        }

        if (showAddDialog || mealToEdit != null) {
            MealDialog(
                meal = mealToEdit,
                onDismiss = {
                    showAddDialog = false
                    mealToEdit = null
                },
                onConfirm = { name, category, calories, description ->
                    if (mealToEdit != null) {
                        viewModel.updateCustomMeal(mealToEdit!!.copy(
                            name = name,
                            category = category,
                            calories = calories,
                            description = description
                        ))
                    } else {
                        viewModel.addCustomMeal(name, category, calories, description)
                    }
                    showAddDialog = false
                    mealToEdit = null
                }
            )
        }
    }
}

@Composable
fun MealItem(
    meal: CustomMeal,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(meal.name, style = MaterialTheme.typography.titleMedium)
                Text("${meal.category} • ${meal.calories} kcal", style = MaterialTheme.typography.bodySmall)
                if (meal.description.isNotBlank()) {
                    Text(meal.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Edit")
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealDialog(
    meal: CustomMeal?,
    onDismiss: () -> Unit,
    onConfirm: (String, String, Int, String) -> Unit
) {
    var name by remember { mutableStateOf(meal?.name ?: "") }
    var category by remember { mutableStateOf(meal?.category ?: "Breakfast") }
    var calories by remember { mutableStateOf(meal?.calories?.toString() ?: "") }
    var description by remember { mutableStateOf(meal?.description ?: "") }

    val categories = listOf("Breakfast", "Lunch", "Snack", "Dinner")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (meal == null) "Add Custom Meal" else "Edit Custom Meal") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Meal Name") })
                
                Text("Category", style = MaterialTheme.typography.labelMedium)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    categories.forEach { cat ->
                        FilterChip(
                            selected = category == cat,
                            onClick = { category = cat },
                            label = { Text(cat) }
                        )
                    }
                }

                OutlinedTextField(value = calories, onValueChange = { calories = it }, label = { Text("Calories") })
                OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description (Optional)") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val cal = calories.toIntOrNull() ?: 0
                onConfirm(name, category, cal, description)
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
