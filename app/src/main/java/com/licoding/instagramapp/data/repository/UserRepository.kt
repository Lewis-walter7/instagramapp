package com.licoding.instagramapp.data.repository

import com.licoding.instagramapp.data.models.User
import com.licoding.instagramapp.data.remote.dto.PostResponse
import com.licoding.instagramapp.data.remote.dto.UserResponse
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createUser(user: User): UserResponse
    fun getUsers(): Flow<List<UserResponse>>
    fun getUserPosts(): Flow<List<PostResponse>>

    companion object {
        fun create(): UserRepository{
            return UserRepositoryImpl (
                client = HttpClient(Android) {
                    install(ContentNegotiation){

                    }
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                }
            )
        }
    }
}