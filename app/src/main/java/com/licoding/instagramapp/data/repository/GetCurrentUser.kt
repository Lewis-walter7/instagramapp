package com.licoding.instagramapp.data.repository

import com.licoding.instagramapp.data.models.User
import com.licoding.instagramapp.domain.room.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun GetCurrentUser(
    userDao: UserDao
) : User? {
    return withContext(Dispatchers.IO) {
        userDao.getCurrentUser()
    }
}