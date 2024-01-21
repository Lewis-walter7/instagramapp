package com.licoding.instagramapp.domain.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.licoding.instagramapp.data.models.UserResult

@Dao
interface RecentSearchDao {
    @Upsert
    suspend fun upsertResult(userResult: UserResult)

    @Query("SELECT * FROM userresult")
    suspend fun getResult(): List<UserResult>
}
