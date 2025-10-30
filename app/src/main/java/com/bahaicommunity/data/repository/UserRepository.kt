// FILE: CommunityApp/app/src/main/java/com/bahaicommunity/data/repository/UserRepository.kt
package com.bahaicommunity.data.repository

import com.bahaicommunity.data.api.ApiService
import com.bahaicommunity.data.database.dao.UserDao
import com.bahaicommunity.data.database.entities.User
import com.bahaicommunity.data.database.entities.LoginRequest
import com.bahaicommunity.data.database.entities.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val apiService: ApiService
) {
    
    fun getCurrentUser(): Flow<UserEntity?> {
        return userDao.getCurrentUser()
    }
    
    suspend fun login(email: String, password: String): Result<User> {
        return try {
            val request = LoginRequest(email, password)
            val response = apiService.login(request)
            
            if (response.isSuccessful) {
                response.body()?.let { loginResponse ->
                    val user = User(
                        id = loginResponse.user.id,
                        name = loginResponse.user.name,
                        email = loginResponse.user.email,
                        profileImageUrl = loginResponse.user.profileImageUrl,
                        bio = loginResponse.user.bio
                    )
                    
                    // Update local database
                    userDao.logoutAllUsers()
                    userDao.insertUser(
                        UserEntity(
                            id = user.id,
                            name = user.name,
                            email = user.email,
                            profileImageUrl = user.profileImageUrl,
                            bio = user.bio,
                            isLoggedIn = true
                        )
                    )
                    
                    Result.success(user)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Login failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun logout() {
        userDao.logoutAllUsers()
    }
    
    suspend fun getUserById(id: String): UserEntity? {
        return userDao.getUserById(id)
    }
}
