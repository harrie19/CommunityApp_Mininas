// FILE: CommunityApp/app/src/main/java/com/bahaicommunity/data/repository/PostRepository.kt
package com.bahaicommunity.data.repository

import com.bahaicommunity.data.api.ApiService
import com.bahaicommunity.data.api.models.ApiResponse
import com.bahaicommunity.data.api.models.PostApiResponse
import com.bahaicommunity.data.database.dao.PostDao
import com.bahaicommunity.data.database.entities.PostEntity
import com.bahaicommunity.data.database.entities.CreatePostRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(
    private val postDao: PostDao,
    private val apiService: ApiService
) {
    
    fun getAllPosts(): Flow<List<PostEntity>> {
        return postDao.getAllPosts()
    }
    
    suspend fun getPostById(id: Long): PostEntity? {
        return postDao.getPostById(id)
    }
    
    suspend fun createPost(request: CreatePostRequest): Result<PostEntity> {
        return try {
            val response = apiService.createPost(
                mapOf(
                    "title" to request.title,
                    "content" to request.content,
                    "imageUrl" to request.imageUrl
                )
            )
            
            if (response.isSuccessful) {
                response.body()?.let { apiPost ->
                    val entity = apiPostToEntity(apiPost)
                    postDao.insertPost(entity)
                    Result.success(entity)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Network error: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun uploadImage(
        imagePart: MultipartBody.Part,
        title: RequestBody,
        content: RequestBody
    ): Result<PostEntity> {
        return try {
            val response = apiService.uploadImage(imagePart, title, content)
            
            if (response.isSuccessful) {
                response.body()?.let { apiPost ->
                    val entity = apiPostToEntity(apiPost)
                    postDao.insertPost(entity)
                    Result.success(entity)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Network error: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun syncUnsyncedPosts() {
        val unsyncedPosts = postDao.getUnsyncedPosts()
        
        for (post in unsyncedPosts) {
            try {
                val request = mapOf(
                    "title" to post.title,
                    "content" to post.content,
                    "imageUrl" to post.imageUrl
                )
                
                val response = apiService.createPost(request)
                
                if (response.isSuccessful) {
                    response.body()?.let { apiPost ->
                        postDao.updatePost(post.copy(isSynced = true, id = apiPost.id))
                    }
                }
            } catch (e: Exception) {
                // Continue with other posts
            }
        }
    }
    
    private fun apiPostToEntity(apiPost: PostApiResponse): PostEntity {
        return PostEntity(
            id = apiPost.id,
            title = apiPost.title,
            content = apiPost.content,
            authorName = apiPost.authorName,
            authorId = apiPost.authorId,
            imageUrl = apiPost.imageUrl,
            createdAt = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", 
                java.util.Locale.getDefault()).parse(apiPost.createdAt) ?: java.util.Date(),
            updatedAt = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", 
                java.util.Locale.getDefault()).parse(apiPost.updatedAt) ?: java.util.Date(),
            isSynced = true
        )
    }
}
