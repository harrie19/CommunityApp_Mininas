// FILE: CommunityApp/app/src/main/java/com/bahaicommunity/data/database/BahaiDatabase.kt
package com.bahaicommunity.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.bahaicommunity.data.database.dao.PostDao
import com.bahaicommunity.data.database.dao.UserDao
import com.bahaicommunity.data.database.entities.Converters
import com.bahaicommunity.data.database.entities.PostEntity
import com.bahaicommunity.data.database.entities.UserEntity

@Database(
    entities = [PostEntity::class, UserEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class BahaiCommunityDatabase : RoomDatabase() {
    
    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao
    
    companion object {
        @Volatile
        private var INSTANCE: BahaiCommunityDatabase? = null
        
        fun getDatabase(context: Context): BahaiCommunityDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BahaiCommunityDatabase::class.java,
                    "bahai_community_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
