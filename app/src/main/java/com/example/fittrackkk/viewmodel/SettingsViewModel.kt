package com.example.fittrackkk.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittrackkk.FitTrackApp
import com.example.fittrackkk.data.firebase.FirebaseAuthManager
import com.example.fittrackkk.data.firebase.FirebaseDataManager
import com.example.fittrackkk.data.local.PreferencesManager
import com.example.fittrackkk.data.model.UserProfile
import com.example.fittrackkk.data.repository.DietRepository
import com.example.fittrackkk.data.repository.ExerciseRepository
import com.example.fittrackkk.data.repository.UserRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class SettingsUiState(
    val profile: UserProfile? = null,
    val isDarkMode: Boolean = false,
    val isEditing: Boolean = false,
    val editHeight: String = "",
    val editWeight: String = "",
    val editTargetWeight: String = "",
    val editAge: String = "",
    val editGender: String = "",
    val message: String? = null
)

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val db = (application as FitTrackApp).database
    private val userRepository = UserRepository(db.userProfileDao(), FirebaseAuthManager(), FirebaseDataManager())
    private val dietRepository = DietRepository(db.dietDao())
    private val exerciseRepository = ExerciseRepository(db.exerciseDao())
    private val preferencesManager = PreferencesManager(application)

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState

    init {
        viewModelScope.launch {
            userRepository.getProfile().collect { profile ->
                _uiState.value = _uiState.value.copy(profile = profile)
            }
        }
        viewModelScope.launch {
            preferencesManager.isDarkMode.collect { isDark ->
                _uiState.value = _uiState.value.copy(isDarkMode = isDark)
            }
        }
    }

    fun startEditing() {
        val profile = _uiState.value.profile ?: return
        _uiState.value = _uiState.value.copy(
            isEditing = true,
            editHeight = profile.height.toString(),
            editWeight = profile.weight.toString(),
            editTargetWeight = profile.targetWeight.toString(),
            editAge = profile.age.toString(),
            editGender = profile.gender
        )
    }

    fun cancelEditing() { _uiState.value = _uiState.value.copy(isEditing = false) }

    fun updateEditField(field: String, value: String) {
        _uiState.value = when (field) {
            "height" -> _uiState.value.copy(editHeight = value)
            "weight" -> _uiState.value.copy(editWeight = value)
            "targetWeight" -> _uiState.value.copy(editTargetWeight = value)
            "age" -> _uiState.value.copy(editAge = value)
            "gender" -> _uiState.value.copy(editGender = value)
            else -> _uiState.value
        }
    }

    fun saveProfile() {
        viewModelScope.launch {
            val state = _uiState.value
            val existing = state.profile ?: return@launch
            val updated = existing.copy(
                height = state.editHeight.toFloatOrNull() ?: existing.height,
                weight = state.editWeight.toFloatOrNull() ?: existing.weight,
                targetWeight = state.editTargetWeight.toFloatOrNull() ?: existing.targetWeight,
                age = state.editAge.toIntOrNull() ?: existing.age,
                gender = state.editGender.ifBlank { existing.gender }
            )
            userRepository.updateProfile(updated)
            _uiState.value = _uiState.value.copy(isEditing = false, message = "Profile updated!")
        }
    }

    fun toggleDarkMode() {
        viewModelScope.launch {
            val newValue = !_uiState.value.isDarkMode
            preferencesManager.setDarkMode(newValue)
        }
    }

    fun restartProgress() {
        viewModelScope.launch {
            dietRepository.resetAllProgress()
            exerciseRepository.resetAllProgress()
            _uiState.value = _uiState.value.copy(message = "All progress has been reset!")
        }
    }

    fun clearMessage() { _uiState.value = _uiState.value.copy(message = null) }

    val isDarkMode: Flow<Boolean> = preferencesManager.isDarkMode
}
