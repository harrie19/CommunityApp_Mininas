// FILE: CommunityApp/app/src/main/java/com/bahaicommunity/ui/feed/FeedViewModel.kt
package com.bahaicommunity.ui.feed

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

data class FeedUiState(
    val posts: List<PostEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(FeedUiState())
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()
    
    init {
        loadPosts()
    }
    
    fun loadPosts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                postRepository.getAllPosts().collect { posts ->
                    _uiState.value = FeedUiState(
                        posts = posts,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = FeedUiState(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    
    fun refreshPosts() {
        loadPosts()
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
