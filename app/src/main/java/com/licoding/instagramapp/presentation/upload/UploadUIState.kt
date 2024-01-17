package com.licoding.instagramapp.presentation.upload

import android.net.Uri

 data class UploadUIState(
     var selectedUri: Uri? = null,
     val showAppBar: Boolean = true,
     val selectedUris: List<Uri> = emptyList(),
     val turnOffComments: Boolean = false,
     val hideLikes: Boolean = false,
     val caption: String? = null
 )