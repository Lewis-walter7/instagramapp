package com.licoding.instagramapp.presentation.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.licoding.instagramapp.data.models.User
import com.licoding.instagramapp.domain.room.AppDatabaseSingleton
import kotlinx.coroutines.launch

class MainViewModel(
    private val application: Application
) : ViewModel(){
    private val appDatabase = AppDatabaseSingleton.getDatabase(application)

    var user: User? = null

    init {
        viewModelScope.launch {
            user = appDatabase.userDao.getCurrentUser()!!
        }
    }
    fun logout() {

    }
}