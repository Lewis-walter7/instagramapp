package com.licoding.instagramapp.presentation.upload

import android.net.Uri

 data class UploadUIState(
     var selectedUri: Uri? = null,
     val selectedUris: List<Uri> = emptyList()
 )