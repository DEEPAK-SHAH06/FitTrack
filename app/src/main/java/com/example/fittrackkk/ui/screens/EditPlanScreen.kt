package com.example.fittrackkk.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fittrackkk.data.model.CustomMeal
import com.example.fittrackkk.viewmodel.DietViewModel
import com.example.fittrackkk.viewmodel.ExerciseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPlanScreen(
    dayNumber: Int,
    dietViewModel: DietViewModel,
    exerciseViewModel: ExerciseViewModel,
    onNavigateBack: () -> Unit
) {
    val selectedDay by dietViewModel.selectedDay.collectAsState()
    val dayExercises by exerciseViewModel.selectedDayExercises.collectAsState()
    val dayCustomExercises by exerciseViewModel.selectedDayCustomExercises.collectAsState()
    val customMeals by dietViewModel.customMeals.collectAsState()
    val customExercises by exerciseViewModel.customExercises.collectAsState()
    val allDefaultExercises by exerciseViewModel.allDefaultExercises.collectAsState()

    var showMealSelectionFor by remember { mutableStateOf<String?>(null) }
    var showExerciseSelection by remember { mutableStateOf(false) }

    LaunchedEffect(dayNumber) {
        dietViewModel.loadDay(dayNumber)
        exerciseViewModel.loadDayExercises(dayNumber)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Day $dayNumber Plan") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Text("Diet Plan", style = MaterialTheme.typography.headlineSmall) }
            
            selectedDay?.let { day ->
                item { PlanMealItem("Breakfast", day.breakfast, day.breakfastCalories) { showMealSelectionFor = "Breakfast" } }
                item { PlanMealItem("Lunch", day.lunch, day.lunchCalories) { showMealSelectionFor = "Lunch" } }
                item { PlanMealItem("Snack", day.afternoonSnack, day.afternoonSnackCalories) { showMealSelectionFor = "Snack" } }
                item { PlanMealItem("Dinner", day.dinner, day.dinnerCalories) { showMealSelectionFor = "Dinner" } }
            }

            item { Divider() }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Exercise Plan", style = MaterialTheme.typography.headlineSmall)
                    IconButton(onClick = { showExerciseSelection = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Exercise")
                    }
                }
            }

            items(dayExercises) { exercise ->
                PlanExerciseItem(exercise.name, "${exercise.durationSeconds / 60} mins") {
                    exerciseViewModel.removeExerciseFromDay(dayNumber, exercise.id, false)
                }
            }

            items(dayCustomExercises) { exercise ->
                PlanExerciseItem(exercise.name, "${exercise.durationMinutes} mins") {
                    exerciseViewModel.removeExerciseFromDay(dayNumber, exercise.id, true)
                }
            }
        }

        if (showMealSelectionFor != null) {
            val type = showMealSelectionFor!!
            MealSelectionDialog(
                title = "Select $type",
                customMeals = customMeals.filter { it.category == type },
                onDismiss = { showMealSelectionFor = null },
                onSelect = { customMealId ->
                    dietViewModel.updateDayMeal(dayNumber, type, customMealId)
                    showMealSelectionFor = null
                }
            )
        }

        if (showExerciseSelection) {
            ExerciseSelectionDialog(
                defaultExercises = allDefaultExercises,
                customExercises = customExercises,
                onDismiss = { showExerciseSelection = false },
                onSelect = { id, isCustom ->
                    exerciseViewModel.addExerciseToDay(dayNumber, id, isCustom)
                    showExerciseSelection = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanMealItem(label: String, name: String, calories: Int, onClick: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(label, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
            Text(name, style = MaterialTheme.typography.titleMedium)
            Text("$calories kcal", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun PlanExerciseItem(name: String, duration: String, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(name, style = MaterialTheme.typography.titleMedium)
                Text(duration, style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Remove")
            }
        }
    }
}

@Composable
fun MealSelectionDialog(
    title: String,
    customMeals: List<CustomMeal>,
    onDismiss: () -> Unit,
    onSelect: (Int?) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            LazyColumn {
                item {
                    TextButton(onClick = { onSelect(null) }, modifier = Modifier.fillMaxWidth()) {
                        Text("Reset to Default")
                    }
                }
                items(customMeals) { meal ->
                    TextButton(onClick = { onSelect(meal.id) }, modifier = Modifier.fillMaxWidth()) {
                        Text("${meal.name} (${meal.calories} kcal)")
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
fun ExerciseSelectionDialog(
    defaultExercises: List<com.example.fittrackkk.data.model.Exercise>,
    customExercises: List<com.example.fittrackkk.data.model.CustomExercise>,
    onDismiss: () -> Unit,
    onSelect: (Int, Boolean) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Exercise") },
        text = {
            LazyColumn(modifier = Modifier.heightIn(max = 400.dp)) {
                item { Text("Custom Exercises", style = MaterialTheme.typography.labelLarge) }
                items(customExercises) { ex ->
                    TextButton(onClick = { onSelect(ex.id, true) }, modifier = Modifier.fillMaxWidth()) {
                        Text(ex.name)
                    }
                }
                item { Divider(modifier = Modifier.padding(vertical = 8.dp)) }
                item { Text("Default Exercises", style = MaterialTheme.typography.labelLarge) }
                items(defaultExercises) { ex ->
                    TextButton(onClick = { onSelect(ex.id, false) }, modifier = Modifier.fillMaxWidth()) {
                        Text(ex.name)
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
