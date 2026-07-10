package com.example.fittrackkk.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fittrackkk.data.model.DietDay
import com.example.fittrackkk.ui.theme.*
import com.example.fittrackkk.viewmodel.DietViewModel

@Composable
fun DayDetailScreen(
    dayNumber: Int,
    viewModel: DietViewModel,
    onBack: () -> Unit,
    onStartWorkout: () -> Unit,
    onEditPlan: () -> Unit
) {
    val selectedDay by viewModel.selectedDay.collectAsState()

    LaunchedEffect(dayNumber) {
        viewModel.loadDay(dayNumber)
    }

    DayDetailScreenContent(
        dayNumber = dayNumber,
        day = selectedDay,
        onBack = onBack,
        onStartWorkout = onStartWorkout,
        onEditPlan = onEditPlan
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayDetailScreenContent(
    dayNumber: Int,
    day: DietDay?,
    onBack: () -> Unit,
    onStartWorkout: () -> Unit,
    onEditPlan: () -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Day $dayNumber Details", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    TextButton(onClick = onEditPlan) {
                        Text("Edit Plan", color = MaterialTheme.colorScheme.primary)
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
            day?.let { d ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(16.dp)
                ) {
                    // Header Summary Card
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (d.isCompleted) CardCompleted else MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.LocalFireDepartment,
                                contentDescription = null,
                                tint = WarningOrange,
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = "Total Intake",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = TextSecondaryLight
                                )
                                Text(
                                    text = "${d.totalCalories} Kcal",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                if (d.isCompleted) {
                                    Text(
                                        text = "Day completed! Great job!",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }

                    Text(
                        text = "Today's Meals",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Meal items
                    MealItemCard("Breakfast", d.breakfast, d.breakfastCalories)
                    MealItemCard("Lunch", d.lunch, d.lunchCalories)
                    MealItemCard("Afternoon Snack", d.afternoonSnack, d.afternoonSnackCalories)
                    MealItemCard("Dinner", d.dinner, d.dinnerCalories)
                    
                    Spacer(modifier = Modifier.height(24.dp))

                    // Start Workout Button
                    Button(
                        onClick = onStartWorkout,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (d.isCompleted) SuccessGreen else MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            imageVector = if (d.isCompleted) Icons.Default.Check else Icons.Default.PlayArrow,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = if (d.isCompleted) "Workout Completed" else "Start Workout",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(40.dp))
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

@Composable
fun MealItemCard(
    category: String,
    mealName: String,
    calories: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = category,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = mealName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
            }
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "$calories kcal",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DayDetailScreenPreview() {
    FitTrackkkTheme {
        DayDetailScreenContent(
            dayNumber = 3,
            day = DietDay(
                dayNumber = 3,
                breakfast = "Sel Roti with Aloo Dum",
                breakfastCalories = 450,
                lunch = "Dal Bhat",
                lunchCalories = 600,
                afternoonSnack = "Bhatmas",
                afternoonSnackCalories = 150,
                dinner = "Veg Soup",
                dinnerCalories = 300,
                isCompleted = false
            ),
            onBack = {},
            onStartWorkout = {},
            onEditPlan = {}
        )
    }
}
