package com.example.fittrackkk.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittrackkk.FitTrackApp
import com.example.fittrackkk.data.firebase.FirebaseAuthManager
import com.example.fittrackkk.data.firebase.FirebaseDataManager
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

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val db = (application as FitTrackApp).database
    private val userRepository = UserRepository(
        db.userProfileDao(),
        FirebaseAuthManager(),
        FirebaseDataManager()
    )

    private val _uiState = MutableStateFlow(AuthUiState(isLoggedIn = userRepository.isLoggedIn))
    val uiState: StateFlow<AuthUiState> = _uiState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val result = userRepository.signIn(email, password)
            _uiState.value = if (result.isSuccess) {
                _uiState.value.copy(isLoading = false, isLoggedIn = true)
            } else {
                _uiState.value.copy(isLoading = false, errorMessage = result.exceptionOrNull()?.message ?: "Sign in failed")
            }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val result = userRepository.signUp(email, password)
            _uiState.value = if (result.isSuccess) {
                _uiState.value.copy(isLoading = false, isSignUpSuccess = true, isLoggedIn = true)
            } else {
                _uiState.value.copy(isLoading = false, errorMessage = result.exceptionOrNull()?.message ?: "Sign up failed")
            }
        }
    }

    fun signOut() {
        userRepository.signOut()
        _uiState.value = AuthUiState()
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun clearSignUpSuccess() {
        _uiState.value = _uiState.value.copy(isSignUpSuccess = false)
    }
}
