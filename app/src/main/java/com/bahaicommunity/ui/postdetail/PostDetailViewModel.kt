// FILE: CommunityApp/app/src/main/java/com/bahaicommunity/ui/postdetail/PostDetailViewModel.kt
package com.bahaicommunity.ui.postdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bahaicommunity.data.database.entities.PostEntity
import com.bahaicommunity.data.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PostDetailUiState(
    val post: PostEntity? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(PostDetailUiState())
    val uiState: StateFlow<PostDetailUiState> = _uiState.asStateFlow()
    
    fun loadPost(postId: Long) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        
        viewModelScope.launch {
            try {
                val post = postRepository.getPostById(postId)
                _uiState.value = PostDetailUiState(
                    post = post,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = PostDetailUiState(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
