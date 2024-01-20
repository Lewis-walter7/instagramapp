package com.licoding.instagramapp.data.remote.post

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.io.IOException

private val firebaseRef = Firebase.storage.reference

suspend fun UploadFile(selectedUri: Uri?, context: Context): Uri?{
    return try {
        withContext(Dispatchers.IO) {
            selectedUri?.let {
                firebaseRef.child("images/${it.lastPathSegment}").putFile(it).await()
            }
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(context, "Successfully Uploaded", Toast.LENGTH_SHORT).show()
            }
            firebaseRef.child("images/${selectedUri?.lastPathSegment}").downloadUrl.await()
        }
    } catch (e: IOException) {
        withContext(Dispatchers.Main) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            println(e.message)
            println(e.stackTrace)
        }
        null
    }
}