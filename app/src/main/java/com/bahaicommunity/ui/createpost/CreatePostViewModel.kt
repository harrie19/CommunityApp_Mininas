// FILE: CommunityApp/app/src/main/java/com/bahaicommunity/ui/createpost/CreatePostViewModel.kt
package com.bahaicommunity.ui.createpost

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bahaicommunity.data.database.entities.CreatePostRequest
import com.bahaicommunity.data.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

data class CreatePostUiState(
    val title: String = "",
    val content: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val imageUri: Uri? = null
)

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(CreatePostUiState())
    val uiState: StateFlow<CreatePostUiState> = _uiState.asStateFlow()
    
    fun updateImageUri(uri: Uri?) {
        _uiState.value = _uiState.value.copy(imageUri = uri)
    }
    
    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }
    
    fun updateContent(content: String) {
        _uiState.value = _uiState.value.copy(content = content)
    }
    
    fun createPost(title: String, content: String, imageUri: Uri?) {
        if (title.isBlank() || content.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "Title and content are required")
            return
        }
        
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        
        viewModelScope.launch {
            try {
                val request = CreatePostRequest(
                    title = title,
                    content = content,
                    imageUrl = null
                )
                
                postRepository.createPost(request).fold(
                    onSuccess = { post ->
                        _uiState.value = CreatePostUiState(
                            isLoading = false,
                            isSuccess = true,
                            imageUri = null
                        )
                    },
                    onFailure = { error ->
                        _uiState.value = CreatePostUiState(
                            isLoading = false,
                            error = error.message
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = CreatePostUiState(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    
    fun clearState() {
        _uiState.value = CreatePostUiState()
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
