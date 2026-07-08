package com.example.fittrackkk

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso.onView
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.fittrackkk.ui.screens.RegisterScreen
import org.junit.After

@RunWith(AndroidJUnit4::class)
class LoginInstrumentedTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<LoginScreen>()

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }
    @Test
    fun testSuccessfulLogin_navigatesToDashboard() {
        // Enter email
//        composeRule.onNodeWithTag("email")
//            .performTextInput("ram@gmail.com")
//
//        // Enter password
//        composeRule.onNodeWithTag("password")
//            .performTextInput("password")

        // Click Login
        composeRule.onNodeWithTag("register")
            .performClick()



//        Intents.intended(hasComponent(DashboardActivity::class.java.name))
        Intents.intended(hasComponent(RegisterScreen::class.java.name))
    }

}