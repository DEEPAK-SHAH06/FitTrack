package com.example.fittrackkk

import android.app.Application
import com.example.fittrackkk.data.repository.UserRepository
import com.example.fittrackkk.viewmodel.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var application: FitTrackApp
    private lateinit var userRepository: UserRepository
    private lateinit var viewModel: AuthViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        application = mock()
        userRepository = mock()
        
        // Mock the database if needed, but here we pass the repository directly
        // Note: AuthViewModel(application, userRepository) will use actualUserRepository = userRepository
        viewModel = AuthViewModel(application, userRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun signIn_success_updatesUiState() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        whenever(userRepository.signIn(email, password)).thenReturn(Result.success(Unit))
        whenever(userRepository.isLoggedIn).thenReturn(false)

        // When
        viewModel.signIn(email, password)
        
        // Assert loading
        assertTrue(viewModel.uiState.value.isLoading)
        
        advanceUntilIdle()

        // Then
        assertFalse(viewModel.uiState.value.isLoading)
        assertTrue(viewModel.uiState.value.isLoggedIn)
        assertEquals(null, viewModel.uiState.value.errorMessage)
    }

    @Test
    fun signIn_failure_updatesUiStateWithError() = runTest {
        // Given
        val email = "test@example.com"
        val password = "wrong_password"
        val errorMsg = "Invalid credentials"
        whenever(userRepository.signIn(email, password)).thenReturn(Result.failure(Exception(errorMsg)))

        // When
        viewModel.signIn(email, password)
        
        advanceUntilIdle()

        // Then
        assertFalse(viewModel.uiState.value.isLoading)
        assertFalse(viewModel.uiState.value.isLoggedIn)
        assertEquals(errorMsg, viewModel.uiState.value.errorMessage)
    }

    @Test
    fun signUp_success_updatesUiState() = runTest {
        // Given
        val email = "new@example.com"
        val password = "password123"
        whenever(userRepository.signUp(email, password)).thenReturn(Result.success(Unit))

        // When
        viewModel.signUp(email, password)
        
        advanceUntilIdle()

        // Then
        assertFalse(viewModel.uiState.value.isLoading)
        assertTrue(viewModel.uiState.value.isSignUpSuccess)
        assertTrue(viewModel.uiState.value.isLoggedIn)
    }
}
