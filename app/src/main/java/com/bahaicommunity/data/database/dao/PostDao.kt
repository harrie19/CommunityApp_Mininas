// FILE: CommunityApp/app/src/main/java/com/bahaicommunity/data/database/dao/PostDao.kt
package com.bahaicommunity.data.database.dao

import androidx.room.*
import com.bahaicommunity.data.database.entities.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    
    @Query("SELECT * FROM posts ORDER BY createdAt DESC")
    fun getAllPosts(): Flow<List<PostEntity>>
    
    @Query("SELECT * FROM posts WHERE id = :id")
    suspend fun getPostById(id: Long): PostEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: PostEntity): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)
    
    @Update
    suspend fun updatePost(post: PostEntity)
    
    @Delete
    suspend fun deletePost(post: PostEntity)
    
    @Query("DELETE FROM posts WHERE isSynced = 0")
    suspend fun deleteUnsyncedPosts()
    
    @Query("SELECT * FROM posts WHERE isSynced = 0")
    suspend fun getUnsyncedPosts(): List<PostEntity>
}
