package com.example.fittrackkk.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fittrackkk.ui.theme.*
import com.example.fittrackkk.viewmodel.AuthViewModel
import com.example.fittrackkk.viewmodel.SettingsViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    onNavigateToUserInfo: () -> Unit,
    authViewModel: AuthViewModel,
    settingsViewModel: SettingsViewModel
) {
    val scale = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }
    val authState by authViewModel.uiState.collectAsState()
    val settingsState by settingsViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        scale.animateTo(1f, animationSpec = tween(800, easing = EaseOutBack))
        alpha.animateTo(1f, animationSpec = tween(600))
        delay(1500)
        when {
            !authState.isLoggedIn -> onNavigateToLogin()
            settingsState.profile?.isProfileComplete != true -> onNavigateToUserInfo()
            else -> onNavigateToDashboard()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(GradientStart, GradientEnd))),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.FitnessCenter,
                contentDescription = "FitTrack",
                modifier = Modifier.size(100.dp).scale(scale.value),
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "FitTrack",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.alpha(alpha.value)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Your Fitness Journey Starts Here",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                modifier = Modifier.alpha(alpha.value)
            )
        }
    }
}
