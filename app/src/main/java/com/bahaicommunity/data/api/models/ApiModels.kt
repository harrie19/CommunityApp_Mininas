// FILE: CommunityApp/app/src/main/java/com/bahaicommunity/data/api/models/ApiModels.kt
package com.bahaicommunity.data.api.models

data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val message: String? = null,
    val error: String? = null
)

data class PostApiResponse(
    val id: Long,
    val title: String,
    val content: String,
    val authorName: String,
    val authorId: String,
    val imageUrl: String? = null,
    val createdAt: String,
    val updatedAt: String
)

data class UserApiResponse(
    val id: String,
    val name: String,
    val email: String,
    val profileImageUrl: String? = null,
    val bio: String? = null
)

data class LoginRequestApi(
    val email: String,
    val password: String
)

data class LoginResponseApi(
    val token: String,
    val user: UserApiResponse
)
