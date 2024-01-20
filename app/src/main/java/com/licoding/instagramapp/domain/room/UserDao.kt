package com.licoding.instagramapp.domain.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.licoding.instagramapp.data.models.User

@Dao
interface UserDao {
    @Upsert
    suspend fun upsertUser(user: User)
    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getUser(username: String): User?
    @Query("DELETE from user")
    suspend fun deleteUserByUserId()

    @Query("SELECT * FROM user")
    suspend fun getCurrentUser(): User
}

