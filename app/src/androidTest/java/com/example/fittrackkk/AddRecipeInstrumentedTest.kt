package com.example.fittrackkk

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.fittrackkk.ui.screens.AddEditRecipeScreen
import com.example.fittrackkk.ui.theme.FitTrackkkTheme
import com.example.fittrackkk.viewmodel.DiscoverViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddRecipeInstrumentedTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun testAddRecipeValidationAndSave() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as FitTrackApp
        val viewModel = DiscoverViewModel(appContext)

        composeRule.setContent {
            FitTrackkkTheme {
                AddEditRecipeScreen(
                    recipeId = null,
                    viewModel = viewModel,
                    onNavigateBack = {}
                )
            }
        }

        // 1. Check if save button is disabled initially
        composeRule.onNodeWithTag("saveRecipeButton").assertExists()
        
        // 2. Fill in title
        composeRule.onNodeWithTag("recipeTitleInput").performTextInput("Healthy Salad")
        
        // 3. Fill in ingredients
        composeRule.onNodeWithTag("recipeIngredientsInput").performTextInput("Lettuce\nTomato\nCucumber")
        
        // 4. Fill in steps
        composeRule.onNodeWithTag("recipeStepsInput").performTextInput("1. Wash veg\n2. Cut veg\n3. Mix")
        
        // 5. Fill in other fields
        composeRule.onNodeWithTag("recipeCaloriesInput").performTextInput("150")
        composeRule.onNodeWithTag("recipePrepTimeInput").performTextInput("10")
        
        // 6. Save button should be enabled now
        composeRule.onNodeWithTag("saveRecipeButton").assertIsEnabled()
        
        // 7. Perform save
        composeRule.onNodeWithTag("saveRecipeButton").performClick()
    }
}
