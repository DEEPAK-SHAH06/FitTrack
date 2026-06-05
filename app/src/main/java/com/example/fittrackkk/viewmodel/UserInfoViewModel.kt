package com.example.fittrackkk.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittrackkk.FitTrackApp
import com.example.fittrackkk.data.firebase.FirebaseAuthManager
import com.example.fittrackkk.data.firebase.FirebaseDataManager
import com.example.fittrackkk.data.local.PreferencesManager
import com.example.fittrackkk.data.model.UserProfile
import com.example.fittrackkk.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UserInfoUiState(
    val height: String = "",
    val weight: String = "",
    val targetWeight: String = "",
    val age: String = "",
    val gender: String = "",
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val errorMessage: String? = null
)

class UserInfoViewModel(application: Application) : AndroidViewModel(application) {
    private val db = (application as FitTrackApp).database
    private val userRepository = UserRepository(db.userProfileDao(), FirebaseAuthManager(), FirebaseDataManager())
    private val preferencesManager = PreferencesManager(application)

    private val _uiState = MutableStateFlow(UserInfoUiState())
    val uiState: StateFlow<UserInfoUiState> = _uiState

    fun updateHeight(value: String) { _uiState.value = _uiState.value.copy(height = value) }
    fun updateWeight(value: String) { _uiState.value = _uiState.value.copy(weight = value) }
    fun updateTargetWeight(value: String) { _uiState.value = _uiState.value.copy(targetWeight = value) }
    fun updateAge(value: String) { _uiState.value = _uiState.value.copy(age = value) }
    fun updateGender(value: String) { _uiState.value = _uiState.value.copy(gender = value) }

    fun saveUserInfo() {
        val state = _uiState.value
        if (state.height.isBlank() || state.weight.isBlank() || state.age.isBlank() || state.gender.isBlank()) {
            _uiState.value = state.copy(errorMessage = "Please fill in all fields")
            return
        }
        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true)
            try {
                val profile = UserProfile(
                    email = userRepository.currentUser?.email ?: "",
                    height = state.height.toFloatOrNull() ?: 0f,
                    weight = state.weight.toFloatOrNull() ?: 0f,
                    targetWeight = state.targetWeight.toFloatOrNull() ?: 0f,
                    age = state.age.toIntOrNull() ?: 0,
                    gender = state.gender,
                    isProfileComplete = true
                )
                userRepository.saveProfile(profile)
                preferencesManager.setFirstTimeSetupComplete(true)
                _uiState.value = _uiState.value.copy(isLoading = false, isSaved = true)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = e.message)
            }
        }
    }
}
