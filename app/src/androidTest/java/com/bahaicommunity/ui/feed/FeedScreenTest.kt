// FILE: CommunityApp/app/src/androidTest/java/com/bahaicommunity/ui/feed/FeedScreenTest.kt
package com.bahaicommunity.ui.feed

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.bahaicommunity.data.database.entities.PostEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date

class FeedScreenTest {
    
    @get:Rule
    val composeTestRule = createComposeRule()
    
    private lateinit var viewModel: FeedViewModel
    
    @Before
    fun setup() {
        viewModel = FeedViewModel(mockPostRepository())
    }
    
    @Test
    fun feedScreen_displaysTitle() {
        composeTestRule.setContent {
            FeedScreen(
                onNavigateToCreatePost = { },
                onNavigateToPostDetail = { },
                onNavigateToProfile = { },
                onNavigateToChat = { },
                viewModel = viewModel
            )
        }
        
        composeTestRule
            .onNodeWithText("Bahai Community")
            .assertIsDisplayed()
    }
    
    @Test
    fun feedScreen_displaysPosts() {
        composeTestRule.setContent {
            FeedScreen(
                onNavigateToCreatePost = { },
                onNavigateToPostDetail = { },
                onNavigateToProfile = { },
                onNavigateToChat = { },
                viewModel = viewModel
            )
        }
        
        composeTestRule
            .onNodeWithText("Test Post")
            .assertIsDisplayed()
            
        composeTestRule
            .onNodeWithText("Test Author")
            .assertIsDisplayed()
    }
    
    @Test
    fun feedScreen_navigatesToCreatePost() {
        var navigateClicked = false
        
        composeTestRule.setContent {
            FeedScreen(
                onNavigateToCreatePost = { navigateClicked = true },
                onNavigateToPostDetail = { },
                onNavigateToProfile = { },
                onNavigateToChat = { },
                viewModel = viewModel
            )
        }
        
        composeTestRule
            .onNodeWithText("Create Post")
            .performClick()
        
        assert(navigateClicked)
    }
    
    private fun mockPostRepository(): com.bahaicommunity.data.repository.PostRepository {
        return object : com.bahaicommunity.data.repository.PostRepository(
            mockDao(),
            mockApiService()
        ) {
            override fun getAllPosts() = flowOf(
                listOf(
                    PostEntity(
                        id = 1,
                        title = "Test Post",
                        content = "Test Content",
                        authorName = "Test Author",
                        authorId = "test-id",
                        createdAt = Date(),
                        updatedAt = Date()
                    )
                )
            )
        }
    }
    
    private fun mockDao(): com.bahaicommunity.data.database.dao.PostDao {
        return object : com.bahaicommunity.data.database.dao.PostDao {
            override fun getAllPosts(): kotlinx.coroutines.flow.Flow<List<PostEntity>> = flowOf(emptyList())
            override suspend fun getPostById(id: Long): PostEntity? = null
            override suspend fun insertPost(post: PostEntity): Long = 1
            override suspend fun insertPosts(posts: List<PostEntity>) {}
            override suspend fun updatePost(post: PostEntity) {}
            override suspend fun deletePost(post: PostEntity) {}
            override suspend fun deleteUnsyncedPosts() {}
            override suspend fun getUnsyncedPosts(): List<PostEntity> = emptyList()
        }
    }
    
    private fun mockApiService(): com.bahaicommunity.data.api.ApiService {
        return object : com.bahaicommunity.data.api.ApiService {
            override suspend fun getPosts(): retrofit2.Response<List<com.bahaicommunity.data.api.models.PostApiResponse>> = retrofit2.Response.success(emptyList())
            override suspend fun getPost(id: Long): retrofit2.Response<com.bahaicommunity.data.api.models.PostApiResponse> = retrofit2.Response.success(null)
            override suspend fun createPost(post: Map<String, *>): retrofit2.Response<com.bahaicommunity.data.api.models.PostApiResponse> = retrofit2.Response.success(null)
            override suspend fun uploadImage(image: okhttp3.MultipartBody.Part, title: okhttp3.RequestBody, content: okhttp3.RequestBody): retrofit2.Response<com.bahaicommunity.data.api.models.PostApiResponse> = retrofit2.Response.success(null)
            override suspend fun login(request: com.bahaicommunity.data.api.models.LoginRequestApi): retrofit2.Response<com.bahaicommunity.data.api.models.LoginResponseApi> = retrofit2.Response.success(null)
            override suspend fun getUser(id: String): retrofit2.Response<com.bahaicommunity.data.api.models.UserApiResponse> = retrofit2.Response.success(null)
        }
    }
}
