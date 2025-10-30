// FILE: CommunityApp/app/src/test/java/com/bahaicommunity/ui/feed/FeedViewModelTest.kt
package com.bahaicommunity.ui.feed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bahaicommunity.data.database.entities.PostEntity
import com.bahaicommunity.data.repository.PostRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import java.util.Date

@ExperimentalCoroutinesApi
class FeedViewModelTest {
    
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    
    @get:Rule
    val testDispatcherRule = StandardTestDispatcher()
    
    @Mock
    private lateinit var postRepository: PostRepository
    
    private lateinit var viewModel: FeedViewModel
    
    private val testScope = TestScope(testDispatcherRule)
    
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = FeedViewModel(postRepository)
    }
    
    @Test
    fun `loadPosts should update UI state with posts`() = testScope.runTest {
        val mockPosts = listOf(
            PostEntity(
                id = 1,
                title = "Test Post 1",
                content = "Test Content 1",
                authorName = "Test Author",
                authorId = "test-id",
                createdAt = Date(),
                updatedAt = Date()
            )
        )
        
        whenever(postRepository.getAllPosts()).thenReturn(flowOf(mockPosts))
        
        viewModel.loadPosts()
        
        val uiState = viewModel.uiState.first()
        assertEquals(mockPosts, uiState.posts)
        assertEquals(false, uiState.isLoading)
        assertEquals(null, uiState.error)
    }
    
    @Test
    fun `loadPosts should update UI state with error on exception`() = testScope.runTest {
        val exception = RuntimeException("Network error")
        
        whenever(postRepository.getAllPosts()).thenThrow(exception)
        
        viewModel.loadPosts()
        
        val uiState = viewModel.uiState.first()
        assertEquals(emptyList<PostEntity>(), uiState.posts)
        assertEquals(false, uiState.isLoading)
        assertEquals("Network error", uiState.error)
    }
    
    @Test
    fun `clearError should clear error from UI state`() = testScope.runTest {
        val exception = RuntimeException("Network error")
        whenever(postRepository.getAllPosts()).thenThrow(exception)
        
        viewModel.loadPosts()
        viewModel.clearError()
        
        val uiState = viewModel.uiState.first()
        assertEquals(null, uiState.error)
    }
}
