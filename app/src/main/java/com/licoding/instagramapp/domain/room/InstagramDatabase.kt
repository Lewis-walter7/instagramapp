package com.licoding.instagramapp.domain.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.licoding.instagramapp.data.models.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class InstagramDatabase: RoomDatabase() {
    abstract val userDao: UserDao
}