// FILE: CommunityApp/app/src/main/java/com/bahaicommunity/data/api/ApiService.kt
package com.bahaicommunity.data.api

import com.bahaicommunity.data.api.models.LoginRequestApi
import com.bahaicommunity.data.api.models.LoginResponseApi
import com.bahaicommunity.data.api.models.PostApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    @GET("posts")
    suspend fun getPosts(): Response<List<PostApiResponse>>
    
    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: Long): Response<PostApiResponse>
    
    @POST("posts")
    suspend fun createPost(
        @Body post: Map<String, @JvmSuppressWildcards Any?>
    ): Response<PostApiResponse>
    
    @Multipart
    @POST("posts/upload")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody
    ): Response<PostApiResponse>
    
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequestApi): Response<LoginResponseApi>
    
    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: String): Response<com.bahaicommunity.data.api.models.UserApiResponse>
}
