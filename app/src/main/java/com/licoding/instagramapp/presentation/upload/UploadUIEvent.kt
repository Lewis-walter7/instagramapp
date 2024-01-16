package com.licoding.instagramapp.presentation.upload

import android.net.Uri

sealed interface UploadUIEvent {
    data class onSelectedUriChange(val uri: Uri): UploadUIEvent
    data class onSelectedUrisChange(val uris: List<Uri>): UploadUIEvent
}