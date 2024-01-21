package com.licoding.instagramapp.presentation.main

import android.net.Uri
import com.licoding.instagramapp.data.models.User
import com.licoding.instagramapp.data.models.UserResult

data class MainUIState(
    val currentUser: User? = null,
    var selectedUri: Uri? = null,
    val showBottomBar: Boolean = true,
    val name: String? = null,
    val bio:String? = null,
    val requestedUsername:String? = null,
    val searchUsers: List<UserResult> = emptyList(),
    val searchUsername: String? = null,
    val searchedUser: User? = null
)