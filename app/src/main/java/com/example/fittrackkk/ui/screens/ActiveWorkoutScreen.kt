package com.example.fittrackkk.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import com.example.fittrackkk.data.model.Exercise
import com.example.fittrackkk.ui.theme.GradientStart
import com.example.fittrackkk.ui.theme.SuccessGreen
import com.example.fittrackkk.ui.theme.TextSecondaryLight
import com.example.fittrackkk.ui.theme.FitTrackkkTheme
import com.example.fittrackkk.viewmodel.ExerciseViewModel
import kotlinx.coroutines.delay

@Composable
fun ActiveWorkoutScreen(
    dayNumber: Int,
    viewModel: ExerciseViewModel,
    onBack: () -> Unit
) {
    val exercises by viewModel.selectedDayExercises.collectAsState()

    LaunchedEffect(dayNumber) {
        viewModel.loadDayExercises(dayNumber)
    }

    ActiveWorkoutScreenContent(
        dayNumber = dayNumber,
        exercises = exercises,
        onBack = onBack,
        onWorkoutCompleted = {
            viewModel.completeWorkout(dayNumber)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveWorkoutScreenContent(
    dayNumber: Int,
    exercises: List<Exercise>,
    onBack: () -> Unit,
    onWorkoutCompleted: () -> Unit
) {
    var currentIndex by remember { mutableStateOf(0) }
    var timeLeft by remember { mutableStateOf(0) }
    var isPaused by remember { mutableStateOf(false) }
    var workoutFinished by remember { mutableStateOf(false) }

    // Initialize timer for current exercise
    LaunchedEffect(exercises, currentIndex) {
        if (exercises.isNotEmpty() && currentIndex < exercises.size) {
            timeLeft = exercises[currentIndex].durationSeconds
            isPaused = false
        }
    }

    // Timer countdown loop
    LaunchedEffect(timeLeft, isPaused, workoutFinished) {
        if (!isPaused && timeLeft > 0 && !workoutFinished) {
            delay(1000)
            timeLeft -= 1
            if (timeLeft == 0) {
                if (currentIndex + 1 < exercises.size) {
                    currentIndex += 1
                } else {
                    onWorkoutCompleted()
                    workoutFinished = true
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Workout Active", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
            if (exercises.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (workoutFinished) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Success",
                        tint = SuccessGreen,
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Workout Complete!",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Great job finishing Day $dayNumber exercises!",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondaryLight,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                    Button(
                        onClick = onBack,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Finish", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            } else {
                val currentExercise = exercises[currentIndex]
                val progress = (currentIndex + 1).toFloat() / exercises.size

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .padding(bottom = 4.dp),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                    Text(
                        text = "Exercise ${currentIndex + 1} of ${exercises.size}",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondaryLight,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(bottom = 24.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier
                                        .size(120.dp)
                                        .background(
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.Default.FitnessCenter,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(60.dp)
                                    )
                                }
                            }
                        }
                    }

                    Text(
                        text = currentExercise.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = currentExercise.description.ifBlank { "Keep your form steady and follow the timer." },
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondaryLight,
                        modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
                    )

                    val minutes = timeLeft / 60
                    val seconds = timeLeft % 60
                    val timeString = String.format("%02d:%02d", minutes, seconds)

                    Text(
                        text = timeString,
                        fontSize = 54.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = {
                                if (currentIndex > 0) {
                                    currentIndex -= 1
                                }
                            },
                            enabled = currentIndex > 0
                        ) {
                            Text(
                                "Previous",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (currentIndex > 0) MaterialTheme.colorScheme.primary else TextSecondaryLight.copy(alpha = 0.5f)
                            )
                        }

                        Button(
                            onClick = { isPaused = !isPaused },
                            modifier = Modifier
                                .height(56.dp)
                                .width(160.dp),
                            shape = RoundedCornerShape(28.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Icon(
                                imageVector = if (isPaused) Icons.Default.PlayArrow else Icons.Default.Pause,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isPaused) "RESUME" else "PAUSE",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        TextButton(
                            onClick = {
                                if (currentIndex + 1 < exercises.size) {
                                    currentIndex += 1
                                } else {
                                    onWorkoutCompleted()
                                    workoutFinished = true
                                }
                            }
                        ) {
                            Text(
                                "SKIP",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Text(
                        text = "Workout Plan",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.5f)
                            .padding(bottom = 8.dp)
                    ) {
                        itemsIndexed(exercises, key = { index, item -> "${item.id}_$index" }) { index, exercise ->
                            ExerciseSummaryRow(
                                exercise = exercise,
                                isCurrent = index == currentIndex,
                                isCompleted = index < currentIndex
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ExerciseSummaryRow(
    exercise: Exercise,
    isCurrent: Boolean,
    isCompleted: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(
                if (isCurrent) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    when {
                        isCompleted -> SuccessGreen
                        isCurrent -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.surfaceVariant
                    },
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isCompleted) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
            } else {
                Text(
                    text = "${exercise.durationSeconds}s",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isCurrent) Color.White else TextSecondaryLight,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = exercise.name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
            color = if (isCurrent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ActiveWorkoutScreenPreview() {
    FitTrackkkTheme {
        ActiveWorkoutScreenContent(
            dayNumber = 5,
            exercises = listOf(
                Exercise(1, "Jumping Jacks", "Warum up", durationSeconds = 60),
                Exercise(2, "Push-ups", "Chest", durationSeconds = 90),
                Exercise(3, "Squats", "Legs", durationSeconds = 90)
            ),
            onBack = {},
            onWorkoutCompleted = {}
        )
    }
}
