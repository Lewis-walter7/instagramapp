package com.licoding.instagramapp.domain.services

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.licoding.instagramapp.data.models.Image

fun PhotoContProvider(
    context: Context,
): List<Image> {
    val images = mutableListOf<Image>()
    val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} ASC"
    val projection = arrayOf(
        MediaStore.Images.Media._ID,
    )
    val cursor = context.contentResolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        projection,
        null,
        null,
        sortOrder
    )

    cursor?.use { itt ->
        val idColumn = itt.getColumnIndex(MediaStore.Images.Media._ID)

        while (itt.moveToNext()) {
            val id = itt.getLong(idColumn)
            val uri = ContentUris.withAppendedId(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                id
            )

            images.add(
                Image(
                    id = id,
                    uri = uri
                )
            )
        }
    }
    return images
}
