package com.licoding.instagramapp.domain.services

import android.content.Context
import android.webkit.MimeTypeMap
import java.util.*


object UriUtils {
    fun getFileExtension(uriString: String): String? {
        val lastDotIndex = uriString.lastIndexOf(".")
        return if (lastDotIndex != -1) {
            uriString.substring(lastDotIndex + 1)
        } else null
    }

    fun getMimeType(context: Context?, uriString: String): String? {
        val extension = getFileExtension(uriString)
        if (extension != null) {
            val mimeTypeMap = MimeTypeMap.getSingleton()
            return mimeTypeMap.getMimeTypeFromExtension(extension.lowercase(Locale.getDefault()))
        }
        return null
    }
}

