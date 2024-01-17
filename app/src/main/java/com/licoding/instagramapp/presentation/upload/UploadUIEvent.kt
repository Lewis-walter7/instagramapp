package com.licoding.instagramapp.presentation.upload

import android.net.Uri

sealed interface UploadUIEvent {
    data class OnSelectedUriChange(val uri: Uri) : UploadUIEvent
    data class OnSelectedUrisChange(val uris: List<Uri>) : UploadUIEvent
    data class ShowAppBar(val isToShow: Boolean) : UploadUIEvent
    data class OnCaptionChange(val caption: String) : UploadUIEvent
    data object OnUploadButtonClicked : UploadUIEvent
    data class OnAllowCommentsChange(val bool: Boolean) : UploadUIEvent

    data class OnHideLikesChange(val bool: Boolean): UploadUIEvent
}