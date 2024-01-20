package com.licoding.instagramapp.presentation.main

import android.net.Uri
import com.licoding.instagramapp.data.models.User

data class MainUIState(
    val currentUser: User? = null,
    var selectedUri: Uri? = null,
    val showBottomBar: Boolean = true,
    val name: String? = null,
    val bio:String? = null,
    val requestedUsername:String? = null,
)