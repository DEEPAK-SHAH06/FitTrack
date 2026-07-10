package com.example.fittrackkk

import com.example.fittrackkk.data.model.Recipe
import com.example.fittrackkk.data.repository.RecipeRepository
import com.example.fittrackkk.viewmodel.RecipeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class RecipeViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var application: FitTrackApp
    private lateinit var repository: RecipeRepository
    private lateinit var viewModel: RecipeViewModel

    private val sampleRecipe = Recipe(
        id = 1,
        title = "Test Recipe",
        category = "Lunch",
        ingredients = "Ingredient 1",
        steps = "Step 1",
        calories = 200,
        prepTimeMinutes = 15,
        imageUrl = "",
        isUserCreated = true
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        application = mock()
        repository = mock()
        
        whenever(repository.getAllRecipes()).thenReturn(flowOf(listOf(sampleRecipe)))
        
        viewModel = RecipeViewModel(application, repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

//    @Test
//    fun recipes_stateFlow_emitsRepositoryData() = runTest {
//        advanceUntilIdle()
//        assertEquals(listOf(sampleRecipe), viewModel.recipes.value)
//    }

    @Test
    fun insertRecipe_callsRepository() = runTest {
        viewModel.insertRecipe(sampleRecipe)
        advanceUntilIdle()
        verify(repository).insertRecipe(sampleRecipe)
    }

    @Test
    fun updateRecipe_callsRepository() = runTest {
        viewModel.updateRecipe(sampleRecipe)
        advanceUntilIdle()
        verify(repository).updateRecipe(sampleRecipe)
    }

    @Test
    fun deleteRecipe_callsRepository() = runTest {
        viewModel.deleteRecipe(sampleRecipe)
        advanceUntilIdle()
        verify(repository).deleteRecipe(sampleRecipe)
    }
}
