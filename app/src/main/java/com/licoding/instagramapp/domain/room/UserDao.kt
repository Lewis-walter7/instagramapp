package com.licoding.instagramapp.domain.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.licoding.instagramapp.data.models.User

@Dao
interface UserDao {
    @Upsert
    suspend fun saveUser(user: User)
    @Query("SELECT * FROM user")
    suspend fun getUser(): User
    @Query("DELETE from user WHERE user.id = :userId")
    suspend fun deleteUserByUserId(userId: String)
}

