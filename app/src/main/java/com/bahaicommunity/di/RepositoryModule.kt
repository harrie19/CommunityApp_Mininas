// FILE: CommunityApp/app/src/main/java/com/bahaicommunity/di/RepositoryModule.kt
package com.bahaicommunity.di

import com.bahaicommunity.data.repository.PostRepository
import com.bahaicommunity.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    
    @Provides
    @Singleton
    fun providePostRepository(
        postDao: com.bahaicommunity.data.database.dao.PostDao,
        apiService: com.bahaicommunity.data.api.ApiService
    ): PostRepository {
        return PostRepository(postDao, apiService)
    }
    
    @Provides
    @Singleton
    fun provideUserRepository(
        userDao: com.bahaicommunity.data.database.dao.UserDao,
        apiService: com.bahaicommunity.data.api.ApiService
    ): UserRepository {
        return UserRepository(userDao, apiService)
    }
}
