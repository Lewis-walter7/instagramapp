package com.licoding.instagramapp.data.remote

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException

class FirebaseService(
    private val context: Context,
    private val selectedUri: Uri
) {
    private val firebaseRef = Firebase.storage.reference
    suspend fun UploadFile(filename: String) {
        try {
            withContext(Dispatchers.IO) {
                selectedUri.let {
                    firebaseRef.child("images/$filename").putFile(it).await()
                    firebaseRef.downloadUrl
                }
                Toast.makeText(context, "Successfully Uploaded", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                println(e.message)
                println(e.stackTrace)
            }
        }
    }
}