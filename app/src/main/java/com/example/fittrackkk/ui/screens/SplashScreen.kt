package com.example.fittrackkk.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fittrackkk.R
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
    val authState by authViewModel.uiState.collectAsState()
    val settingsState by settingsViewModel.uiState.collectAsState()

    SplashScreenContent(
        isLoggedIn = authState.isLoggedIn,
        isProfileComplete = settingsState.profile?.isProfileComplete == true,
        onNavigateToLogin = onNavigateToLogin,
        onNavigateToDashboard = onNavigateToDashboard,
        onNavigateToUserInfo = onNavigateToUserInfo
    )
}

@Composable
fun SplashScreenContent(
    isLoggedIn: Boolean,
    isProfileComplete: Boolean,
    onNavigateToLogin: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    onNavigateToUserInfo: () -> Unit
) {
    val scale = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        scale.animateTo(1f, animationSpec = tween(1000, easing = EaseOutBack))
        alpha.animateTo(1f, animationSpec = tween(800))
        delay(1500)
        if (!isLoggedIn) {
            onNavigateToLogin()
        } else if (!isProfileComplete) {
            onNavigateToUserInfo()
        } else {
            onNavigateToDashboard()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(GradientStart, GradientEnd))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            // Modern App Logo centered with shadow/rounded clip
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "FitTrack Logo",
                modifier = Modifier
                    .size(140.dp)
                    .scale(scale.value)
                    .clip(RoundedCornerShape(28.dp))
            )
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = "FitTrack",
                fontSize = 38.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.alpha(alpha.value)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Your Fitness Journey Starts Here",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f),
                modifier = Modifier.alpha(alpha.value)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    FitTrackkkTheme {
        SplashScreenContent(
            isLoggedIn = false,
            isProfileComplete = false,
            onNavigateToLogin = {},
            onNavigateToDashboard = {},
            onNavigateToUserInfo = {}
        )
    }
}
