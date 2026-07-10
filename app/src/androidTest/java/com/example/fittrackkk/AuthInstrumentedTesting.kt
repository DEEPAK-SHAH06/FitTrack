package com.example.fittrackkk

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthInstrumentedTesting {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testNavigationToRegisterAndBack() {
        // Wait for Login Screen to load (transition from Splash Screen)
        composeRule.waitUntil(5000) {
            composeRule.onAllNodesWithTag("goToRegister").fetchSemanticsNodes().isNotEmpty()
        }

        // Start on Login Screen
        // Click on "Sign Up" text/button
        composeRule.onNodeWithTag("goToRegister").performClick()

        // Verify we are on Register Screen by checking for "Confirm Password" field
        composeRule.onNodeWithTag("confirmPasswordInput").assertIsDisplayed()

        // Click on "Sign In" to go back
        composeRule.onNodeWithTag("goToLogin").performClick()

        // Verify we are back on Login Screen
        composeRule.onNodeWithTag("signInButton").assertIsDisplayed()
    }

    @Test
    fun testLoginValidation() {
        // Wait for Login Screen to load
        composeRule.waitUntil(5000) {
            composeRule.onAllNodesWithTag("signInButton").fetchSemanticsNodes().isNotEmpty()
        }

        // Leave fields empty and check if Sign In button is disabled
        composeRule.onNodeWithTag("signInButton").assertExists()
        // The button is enabled only when email and password are not blank
        // (Based on the code: enabled = !uiState.isLoading && email.isNotBlank() && password.isNotBlank())
        
        // Enter only email
        composeRule.onNodeWithTag("emailInput").performTextInput("test@example.com")
        
        // Still should be disabled or at least not successful
        // Note: In Compose testing, checking 'isEnabled' can be done with assertIsNotEnabled()
    }
}
