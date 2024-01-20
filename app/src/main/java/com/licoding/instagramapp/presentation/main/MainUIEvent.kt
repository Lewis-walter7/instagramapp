package com.licoding.instagramapp.presentation.main

import android.net.Uri

sealed interface MainUIEvent {
    data class OnNameChange(val name: String): MainUIEvent
    data class OnBioChange(val bio: String): MainUIEvent
    data class OnUsernameChange(val username: String): MainUIEvent
    data class OnSelectedUriChange(val selecteduri: Uri): MainUIEvent

    object OnUpdateButtonClicked: MainUIEvent
    data class OnShowBottomBarChange(val value: Boolean): MainUIEvent
}