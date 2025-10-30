// FILE: CommunityApp/app/src/main/java/com/bahaicommunity/data/database/entities/UserEntity.kt
package com.bahaicommunity.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String,
    val profileImageUrl: String? = null,
    val bio: String? = null,
    val isLoggedIn: Boolean = false
)

data class User(
    val id: String,
    val name: String,
    val email: String,
    val profileImageUrl: String? = null,
    val bio: String? = null
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val user: User
)
