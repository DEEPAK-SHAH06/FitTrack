package com.example.fittrackkk.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittrackkk.FitTrackApp
import com.example.fittrackkk.data.firebase.FirebaseAuthManager
import com.example.fittrackkk.data.firebase.FirebaseDataManager
import com.example.fittrackkk.data.repository.DietRepository
import com.example.fittrackkk.data.repository.DiscoverRepository
import com.example.fittrackkk.data.repository.ExerciseRepository
import com.example.fittrackkk.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AuthUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val errorMessage: String? = null,
    val isSignUpSuccess: Boolean = false
)

class AuthViewModel @JvmOverloads constructor(application: Application, private val userRepository: UserRepository? = null) : AndroidViewModel(application) {
    private val db = (application as FitTrackApp).database
    private val actualUserRepository = userRepository ?: UserRepository(
        db.userProfileDao(),
        FirebaseAuthManager(),
        FirebaseDataManager()
    )

    private val _uiState = MutableStateFlow(AuthUiState(isLoggedIn = actualUserRepository.isLoggedIn))
    val uiState: StateFlow<AuthUiState> = _uiState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val result = actualUserRepository.signIn(email, password)
            if (result.isSuccess) {
                val userId = actualUserRepository.currentUser?.uid
                if (userId != null) {
                    try {
                        // Clear existing cache for a new user / fresh start
                        actualUserRepository.clearLocalDataAndReseed(db)

                        // Sync profile from Firebase
                        actualUserRepository.syncProfileFromFirebase()

                        // Sync diet plan and custom meals
                        val dietRepository = DietRepository(db.dietDao(), db.customMealDao())
                        dietRepository.syncFromFirebase(userId)

                        // Sync exercises and custom exercises
                        val exerciseRepository = ExerciseRepository(db.exerciseDao(), db.customExerciseDao())
                        exerciseRepository.syncFromFirebase(userId)

                        // Sync custom recipes
                        val discoverRepository = DiscoverRepository(db.recipeDao(), db.healthArticleDao())
                        discoverRepository.syncFromFirebase(userId)
                    } catch (_: Exception) {
                        // Suppress sync errors to support offline operation
                    }
                }
                _uiState.value = _uiState.value.copy(isLoading = false, isLoggedIn = true)
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message ?: "Sign in failed"
                )
            }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                // Clear existing cache for fresh sign up
                actualUserRepository.clearLocalDataAndReseed(db)
            } catch (_: Exception) {}
            val result = actualUserRepository.signUp(email, password)
            _uiState.value = if (result.isSuccess) {
                _uiState.value.copy(isLoading = false, isSignUpSuccess = true, isLoggedIn = true)
            } else {
                _uiState.value.copy(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message ?: "Sign up failed"
                )
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            actualUserRepository.clearLocalDataAndReseed(db)
            actualUserRepository.signOut()
            _uiState.value = AuthUiState()
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun clearSignUpSuccess() {
        _uiState.value = _uiState.value.copy(isSignUpSuccess = false)
    }

    fun resetState() {
        _uiState.value = AuthUiState(isLoggedIn = false)
    }

    fun getCurrentUserEmail(): String? = actualUserRepository.currentUser?.email
}
