package com.example.fittrackkk.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fittrackkk.data.model.UserProfile
import com.example.fittrackkk.ui.theme.*
import com.example.fittrackkk.viewmodel.AuthViewModel
import com.example.fittrackkk.viewmodel.SettingsUiState
import com.example.fittrackkk.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    authViewModel: AuthViewModel,
    onLoggedOut: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val authState by authViewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearMessage()
        }
    }

    LaunchedEffect(uiState.deleteAccountSuccess) {
        if (uiState.deleteAccountSuccess) {
            authViewModel.resetState()
            onLoggedOut()
        }
    }

    LaunchedEffect(uiState.deleteAccountError) {
        uiState.deleteAccountError?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(authState.isLoggedIn) {
        if (!authState.isLoggedIn) {
            onLoggedOut()
        }
    }

    SettingsScreenContent(
        uiState = uiState,
        onStartEditing = { viewModel.startEditing() },
        onCancelEditing = { viewModel.cancelEditing() },
        onUpdateEditField = { field, value -> viewModel.updateEditField(field, value) },
        onSaveProfile = { viewModel.saveProfile() },
        onToggleDarkMode = { viewModel.toggleDarkMode() },
        onRestartProgress = { viewModel.restartProgress() },
        onDeleteAccount = { viewModel.deleteAccount() },
        onSignOut = {
            authViewModel.signOut()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(
    uiState: SettingsUiState,
    onStartEditing: () -> Unit,
    onCancelEditing: () -> Unit,
    onUpdateEditField: (String, String) -> Unit,
    onSaveProfile: () -> Unit,
    onToggleDarkMode: () -> Unit,
    onRestartProgress: () -> Unit,
    onDeleteAccount: () -> Unit,
    onSignOut: () -> Unit
) {
    val scrollState = rememberScrollState()
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = ErrorRed)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Delete Account", fontWeight = FontWeight.Bold)
                }
            },
            text = { Text("Are you sure you want to delete your account?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDeleteAccount()
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = ErrorRed)
                ) {
                    Text("Delete", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold) },
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
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Profile Section
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Person, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("My Profile", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            }
                            if (!uiState.isEditing) {
                                IconButton(onClick = onStartEditing) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit Profile", tint = MaterialTheme.colorScheme.primary)
                                }
                            } else {
                                Row {
                                    TextButton(onClick = onCancelEditing) {
                                        Text("Cancel")
                                    }
                                    IconButton(onClick = onSaveProfile) {
                                        Icon(Icons.Default.Save, contentDescription = "Save Profile", tint = MaterialTheme.colorScheme.primary)
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        if (uiState.isEditing) {
                            OutlinedTextField(
                                value = uiState.editHeight,
                                onValueChange = { onUpdateEditField("height", it) },
                                label = { Text("Height (cm)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                            )
                            OutlinedTextField(
                                value = uiState.editWeight,
                                onValueChange = { onUpdateEditField("weight", it) },
                                label = { Text("Weight (kg)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                            )
                            OutlinedTextField(
                                value = uiState.editTargetWeight,
                                onValueChange = { onUpdateEditField("targetWeight", it) },
                                label = { Text("Target Weight (kg)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                            )
                            OutlinedTextField(
                                value = uiState.editAge,
                                onValueChange = { onUpdateEditField("age", it) },
                                label = { Text("Age") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                            )
                            OutlinedTextField(
                                value = uiState.editGender,
                                onValueChange = { onUpdateEditField("gender", it) },
                                label = { Text("Gender") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        } else {
                            uiState.profile?.let { profile ->
                                ProfileDetailRow("Height", "${profile.height} cm")
                                ProfileDetailRow("Weight", "${profile.weight} kg")
                                ProfileDetailRow("Target Weight", "${profile.targetWeight} kg")
                                ProfileDetailRow("Age", "${profile.age}")
                                ProfileDetailRow("Gender", profile.gender)
                                Divider(modifier = Modifier.padding(vertical = 8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("BMI", fontWeight = FontWeight.Bold)
                                    Text(
                                        text = String.format("%.1f", profile.bmi),
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            } ?: Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                                Text("No Profile Data", color = TextSecondaryLight)
                            }
                        }
                    }
                }

                // App Settings
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("App Settings", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 12.dp))

                        // Dark Mode
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.DarkMode, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("Dark Mode")
                            }
                            Switch(
                                checked = uiState.isDarkMode,
                                onCheckedChange = { onToggleDarkMode() }
                            )
                        }

                        Divider(modifier = Modifier.padding(vertical = 12.dp))

                        // Restart Progress
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onRestartProgress() }
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Refresh, contentDescription = null, tint = ErrorRed)
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("Restart Progress", color = ErrorRed)
                            }
                            Icon(Icons.Default.Delete, contentDescription = null, tint = ErrorRed)
                        }

                        Divider(modifier = Modifier.padding(vertical = 12.dp))

                        // Delete Account Option
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showDeleteDialog = true }
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Delete, contentDescription = null, tint = ErrorRed)
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("Delete Account", color = ErrorRed)
                            }
                            Icon(Icons.Default.Warning, contentDescription = null, tint = ErrorRed)
                        }
                    }
                }

                // Account Information
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Account Information",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        uiState.profile?.email?.let { email ->
                            if (email.isNotBlank()) {
                                ProfileDetailRow("Email", email)
                            }
                        }
                    }
                }

                // Sign Out Button
                Button(
                    onClick = onSignOut,
                    colors = ButtonDefaults.buttonColors(containerColor = ErrorRed),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(52.dp)
                ) {
                    Icon(Icons.Default.ExitToApp, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Sign Out", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                }

                // Loading Overlay during Account Deletion
                if (uiState.isDeletingAccount) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = ErrorRed)
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileDetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = TextSecondaryLight)
        Text(text = value, fontWeight = FontWeight.Medium)
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    FitTrackkkTheme {
        SettingsScreenContent(
            uiState = SettingsUiState(
                profile = UserProfile(
                    email = "profile@example.com",
                    height = 175f,
                    weight = 70f,
                    targetWeight = 68f,
                    age = 25,
                    gender = "Male"
                ),
                isDarkMode = false,
                isEditing = false,
                authEmail = "auth@example.com"
            ),
            onStartEditing = {},
            onCancelEditing = {},
            onUpdateEditField = { _, _ -> },
            onSaveProfile = {},
            onToggleDarkMode = {},
            onRestartProgress = {},
            onDeleteAccount = {},
            onSignOut = {}
        )
    }
}
