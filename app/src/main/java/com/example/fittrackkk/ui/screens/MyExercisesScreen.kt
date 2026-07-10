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
import com.example.fittrackkk.data.model.CustomExercise
import com.example.fittrackkk.viewmodel.ExerciseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyExercisesScreen(
    viewModel: ExerciseViewModel,
    onNavigateBack: () -> Unit
) {
    val exercises by viewModel.customExercises.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var exerciseToEdit by remember { mutableStateOf<CustomExercise?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Custom Exercises") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                ) {
                Icon(Icons.Default.Add, contentDescription = "Add Exercise")
            }
        }
    ) { padding ->
        if (exercises.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("No custom exercises yet. Add some!")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(exercises, key = { it.id }) { exercise ->
                    ExerciseItem(
                        exercise = exercise,
                        onEdit = { exerciseToEdit = exercise },
                        onDelete = { viewModel.deleteCustomExercise(exercise) }
                    )
                }
            }
        }

        if (showAddDialog || exerciseToEdit != null) {
            ExerciseDialog(
                exercise = exerciseToEdit,
                onDismiss = {
                    showAddDialog = false
                    exerciseToEdit = null
                },
                onConfirm = { name, duration, description ->
                    if (exerciseToEdit != null) {
                        viewModel.updateCustomExercise(exerciseToEdit!!.copy(
                            name = name,
                            durationMinutes = duration,
                            description = description
                        ))
                    } else {
                        viewModel.addCustomExercise(name, duration, description)
                    }
                    showAddDialog = false
                    exerciseToEdit = null
                }
            )
        }
    }
}

@Composable
fun ExerciseItem(
    exercise: CustomExercise,
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
                Text(exercise.name, style = MaterialTheme.typography.titleMedium)
                Text("${exercise.durationMinutes} minutes", style = MaterialTheme.typography.bodySmall)
                if (exercise.description.isNotBlank()) {
                    Text(exercise.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
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
fun ExerciseDialog(
    exercise: CustomExercise?,
    onDismiss: () -> Unit,
    onConfirm: (String, Int, String) -> Unit
) {
    var name by remember { mutableStateOf(exercise?.name ?: "") }
    var duration by remember { mutableStateOf(exercise?.durationMinutes?.toString() ?: "") }
    var description by remember { mutableStateOf(exercise?.description ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (exercise == null) "Add Custom Exercise" else "Edit Custom Exercise") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Exercise Name") })
                OutlinedTextField(value = duration, onValueChange = { duration = it }, label = { Text("Duration (minutes)") })
                OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description (Optional)") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val dur = duration.toIntOrNull() ?: 0
                onConfirm(name, dur, description)
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
