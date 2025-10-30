// FILE: CommunityApp/app/src/main/java/com/bahaicommunity/ui/profile/ProfileViewModel.kt
package com.bahaicommunity.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bahaicommunity.data.database.entities.User
import com.bahaicommunity.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
    
    fun loadCurrentUser() {
        viewModelScope.launch {
            userRepository.getCurrentUser().collect { userEntity ->
                if (userEntity != null) {
                    val user = User(
                        id = userEntity.id,
                        name = userEntity.name,
                        email = userEntity.email,
                        profileImageUrl = userEntity.profileImageUrl,
                        bio = userEntity.bio
                    )
                    _uiState.value = ProfileUiState(user = user)
                } else {
                    _uiState.value = ProfileUiState(user = null)
                }
            }
        }
    }
    
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            userRepository.login(email, password).fold(
                onSuccess = { user ->
                    _uiState.value = ProfileUiState(
                        user = user,
                        isLoading = false
                    )
                },
                onFailure = { error ->
                    _uiState.value = ProfileUiState(
                        isLoading = false,
                        error = error.message
                    )
                }
            )
        }
    }
    
    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
            _uiState.value = ProfileUiState(user = null)
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
