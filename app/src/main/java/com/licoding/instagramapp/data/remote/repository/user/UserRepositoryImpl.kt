package com.licoding.instagramapp.data.remote.repository.user

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import com.licoding.instagramapp.data.models.HttpRoutes
import com.licoding.instagramapp.data.remote.client
import com.licoding.instagramapp.data.remote.dto.AuthResponse
import com.licoding.instagramapp.data.remote.dto.AuthenticationResponse
import com.licoding.instagramapp.data.remote.dto.PostResponse
import com.licoding.instagramapp.data.remote.dto.UserResponse
import com.licoding.instagramapp.domain.requests.EditedUserRequest
import com.licoding.instagramapp.domain.requests.UserRequest
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.IOException

class UserRepositoryImpl(
    private val sharedPreferences: SharedPreferences
): UserRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun createUser(user: UserRequest): UserResponse {
        return withContext(Dispatchers.IO){
            try {
                val response = client.post {
                    url(HttpRoutes.registerRoute)
                    contentType(ContentType.Application.Json)
                    setBody(user)
                }
                loginUser(user)
                response.body<UserResponse>()
            } catch (e: IOException) {
                println("Failed")
                return@withContext UserResponse(
                    username = "",
                    password = "",
                    profileImage = "",
                    email = "",
                    bio = "",
                    id = "",
                    phoneNumber = "",
                    accountType = "",
                    createdAt = 0,
                    followerCount = 0,
                    followingCount = 0,
                    postCount = 0
                )
            }
        }
    }

        @SuppressLint("CommitPrefEdits")
        override suspend fun loginUser(user: UserRequest): AuthResponse {
            return withContext(Dispatchers.IO){
                try {
                    val response = client.post {
                        url(HttpRoutes.LOGINROUTE)
                        contentType(ContentType.Application.Json)
                        setBody(user)
                    }
                    response.body<AuthResponse>()
                } catch (e: IOException) {
                    println(e.message)
                    return@withContext AuthResponse(
                        token = null
                    )
                }
            }
    }

    override fun getUsers(): Flow<List<UserResponse>> {
        TODO("Not yet implemented")
    }

    override fun getUserPosts(): Flow<List<PostResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun authenticate(): AuthenticationResponse {
        return try {
            val response = client.get {
                val token = sharedPreferences.getString("jwt-token", null)
                url(HttpRoutes.AUTHENTICATEROUTE)
                headers{
                    append("Authorization", "Bearer $token")
                }
            }
            response.body<AuthenticationResponse>()
        } catch (e: Exception) {
            println(e.stackTraceToString())
            AuthenticationResponse(isAuthenticated = false)
        }
    }
    override suspend fun getUserInfo(): UserResponse? {
        return withContext(Dispatchers.IO) {
            val token = sharedPreferences.getString("jwt-token", null)

            token?.let {
               val response = client.get {
                    url(HttpRoutes.USERINFOROUTE)
                    headers{
                        append("Authorization", "Bearer $token")
                    }
                }

                response.body<UserResponse>()
            }
        }
    }

    override suspend fun logoutUser() {
        sharedPreferences.edit().remove("jwt-token").apply()
        authenticate()
    }

    override suspend fun updateUser(user: EditedUserRequest) {
        client.patch {
            url(HttpRoutes.UPDATEUSER)
            contentType(ContentType.Application.Json)
            setBody(user)
        }
    }

    override suspend fun updateUserWithUsername(user: EditedUserRequest): AuthResponse {
        val response = client.patch {
            url(HttpRoutes.UPDATEUSER)
            contentType(ContentType.Application.Json)
            setBody(user)
        }
        return response.body<AuthResponse>()
    }

    override suspend fun getUsersByUsername(username: String): List<UserResponse> {
        return withContext(Dispatchers.IO) {
            val response = client.get {
                url(HttpRoutes.GETSEARCHUSERS)
                parameter("username", username)
            }
            response.body()
        }
    }

    override suspend fun getUserById(id: String): UserResponse {
        return withContext(Dispatchers.IO) {
            val response = client.get {
                url(HttpRoutes.GETUSERBYID)
                parameter("userId", id)
            }
            response.body()
        }
    }

    override suspend fun getSearchedUserPosts(id: String): List<PostResponse> {
        val response = client.get {
            url(HttpRoutes.GETSEARCHUSERSPOSTS)
            parameter("id", id)
        }
        return response.body()
    }
}