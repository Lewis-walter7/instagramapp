package com.licoding.instagramapp.data.repository.user

import com.licoding.instagramapp.data.remote.dto.AuthResponse
import com.licoding.instagramapp.data.remote.dto.AuthenticationResponse
import com.licoding.instagramapp.data.remote.dto.PostResponse
import com.licoding.instagramapp.data.remote.dto.UserResponse
import com.licoding.instagramapp.domain.requests.UserRequest
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createUser(user: UserRequest): UserResponse
    suspend fun loginUser(user: UserRequest): AuthResponse
    suspend fun authenticate(): AuthenticationResponse
    fun getUsers(): Flow<List<UserResponse>>
    fun getUserPosts(): Flow<List<PostResponse>>

    suspend fun getUserInfo(): UserResponse
    suspend fun logoutUser()
}