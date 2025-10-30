// FILE: CommunityApp/app/src/main/java/com/bahaicommunity/di/DatabaseModule.kt
package com.bahaicommunity.di

import android.content.Context
import com.bahaicommunity.data.database.BahaiCommunityDatabase
import com.bahaicommunity.data.database.dao.PostDao
import com.bahaicommunity.data.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideBahrainCommunityDatabase(@ApplicationContext context: Context): BahaiCommunityDatabase {
        return BahaiCommunityDatabase.getDatabase(context)
    }
    
    @Provides
    fun providePostDao(database: BahaiCommunityDatabase): PostDao {
        return database.postDao()
    }
    
    @Provides
    fun provideUserDao(database: BahaiCommunityDatabase): UserDao {
        return database.userDao()
    }
}
