// FILE: CommunityApp/app/src/main/java/com/bahaicommunity/data/database/dao/UserDao.kt
package com.bahaicommunity.data.database.dao

import androidx.room.*
import com.bahaicommunity.data.database.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    
    @Query("SELECT * FROM users WHERE isLoggedIn = 1")
    fun getCurrentUser(): Flow<UserEntity?>
    
    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: String): UserEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)
    
    @Update
    suspend fun updateUser(user: UserEntity)
    
    @Delete
    suspend fun deleteUser(user: UserEntity)
    
    @Query("UPDATE users SET isLoggedIn = 0")
    suspend fun logoutAllUsers()
    
    @Query("UPDATE users SET isLoggedIn = 1 WHERE id = :id")
    suspend fun setUserLoggedIn(id: String)
}
