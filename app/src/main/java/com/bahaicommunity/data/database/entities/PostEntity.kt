// FILE: CommunityApp/app/src/main/java/com/bahaicommunity/data/database/entities/PostEntity.kt
package com.bahaicommunity.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "posts")
@TypeConverters(Converters::class)
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val authorName: String,
    val authorId: String,
    val imageUrl: String? = null,
    val createdAt: Date,
    val updatedAt: Date,
    val isSynced: Boolean = false
)

data class CreatePostRequest(
    val title: String,
    val content: String,
    val imageUrl: String? = null
)

data class Post(
    val id: Long,
    val title: String,
    val content: String,
    val authorName: String,
    val authorId: String,
    val imageUrl: String? = null,
    val createdAt: String,
    val updatedAt: String
)
