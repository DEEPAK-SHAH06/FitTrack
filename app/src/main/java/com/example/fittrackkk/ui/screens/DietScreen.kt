package com.example.fittrackkk.ui.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fittrackkk.data.model.DietDay
import com.example.fittrackkk.ui.theme.*
import com.example.fittrackkk.viewmodel.DietViewModel

@Composable
fun DietScreen(
    viewModel: DietViewModel,
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToEdit: (Int) -> Unit,
    onNavigateToCustom: () -> Unit
) {
    val dietDays by viewModel.dietDays.collectAsState()
    val completedCount by viewModel.completedDaysCount.collectAsState()

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNavigateToCustom,
                icon = { Icon(Icons.Default.RestaurantMenu, null) },
                text = { Text("My Meals") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Banner Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(GradientStart, GradientEnd)
                        )
                    )
                    .padding(24.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "My Diet Plan",
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            imageVector = Icons.Default.RestaurantMenu,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.8f),
                            modifier = Modifier.size(36.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$completedCount of 30 days completed",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { completedCount / 30f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = Color.White.copy(alpha = 0.3f),
                    )
                }
            }

            // Grid of 30 days
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(dietDays) { day ->
                    DietDayCard(
                        day = day,
                        onClick = { onNavigateToDetail(day.dayNumber) },
                        onEditClick = { onNavigateToEdit(day.dayNumber) },
                        onCompleteToggle = { viewModel.markDayCompleted(day.dayNumber) }
                    )
                }
            }
        }
    }
}

@Composable
fun DietDayCard(
    day: DietDay,
    onClick: () -> Unit,
    onEditClick: () -> Unit,
    onCompleteToggle: () -> Unit
) {
    val isDark = isSystemInDarkTheme()
    val cardColor = if (day.isCompleted) {
        if (isDark) CardCompletedDark else CardCompleted
    } else {
        if (isDark) SurfaceDark else CardWhite
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .aspectRatio(1f)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Day",
                    fontSize = 12.sp,
                    color = if (day.isCompleted) MaterialTheme.colorScheme.primary else TextSecondaryLight,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${day.dayNumber}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (day.isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }

            // Top Row for Check indicator and Edit button
            Row(
                modifier = Modifier.fillMaxWidth().align(Alignment.TopEnd),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onEditClick,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                        modifier = Modifier.size(16.dp)
                    )
                }

                if (day.isCompleted) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Completed",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { onCompleteToggle() }
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                            .clickable { onCompleteToggle() }
                    )
                }
            }
        }
    }
}
