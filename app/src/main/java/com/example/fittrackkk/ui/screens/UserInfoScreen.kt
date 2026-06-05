package com.example.fittrackkk.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fittrackkk.ui.theme.*
import com.example.fittrackkk.viewmodel.UserInfoUiState
import com.example.fittrackkk.viewmodel.UserInfoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoScreen(
    viewModel: UserInfoViewModel,
    onNavigateToDashboard: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    var showGenderMenu by remember { mutableStateOf(false) }
    val genders = listOf("Male", "Female", "Other")

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            onNavigateToDashboard()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("About You", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Help us personalize your plan",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextSecondaryLight,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                // Height Field
                OutlinedTextField(
                    value = uiState.height,
                    onValueChange = { viewModel.updateHeight(it) },
                    label = { Text("Height (cm)") },
                    leadingIcon = { Icon(Icons.Default.Height, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Weight Field
                OutlinedTextField(
                    value = uiState.weight,
                    onValueChange = { viewModel.updateWeight(it) },
                    label = { Text("Current Weight (kg)") },
                    leadingIcon = { Icon(Icons.Default.MonitorWeight, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Target Weight Field
                OutlinedTextField(
                    value = uiState.targetWeight,
                    onValueChange = { viewModel.updateTargetWeight(it) },
                    label = { Text("Target Weight (kg)") },
                    leadingIcon = { Icon(Icons.Default.MonitorWeight, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Age Field
                OutlinedTextField(
                    value = uiState.age,
                    onValueChange = { viewModel.updateAge(it) },
                    label = { Text("Age") },
                    leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Gender Selector
                ExposedDropdownMenuBox(
                    expanded = showGenderMenu,
                    onExpandedChange = { showGenderMenu = !showGenderMenu }
                ) {
                    OutlinedTextField(
                        value = uiState.gender,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Gender") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showGenderMenu) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                    ExposedDropdownMenu(
                        expanded = showGenderMenu,
                        onDismissRequest = { showGenderMenu = false }
                    ) {
                        genders.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption) },
                                onClick = {
                                    viewModel.updateGender(selectionOption)
                                    showGenderMenu = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Error Msg
                AnimatedVisibility(visible = uiState.errorMessage != null) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = ErrorRed.copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                    ) {
                        Text(
                            uiState.errorMessage ?: "",
                            modifier = Modifier.padding(12.dp),
                            color = ErrorRed,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                // Submit Button
                Button(
                    onClick = { viewModel.saveUserInfo() },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !uiState.isLoading
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                    } else {
                        Text("Save & Get Started", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}
