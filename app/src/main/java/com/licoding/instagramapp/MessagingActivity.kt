package com.licoding.instagramapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.example.compose.InstagramappTheme

class MessagingActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstagramappTheme {
                Text(
                    text = "Messaging Activity"
                )
            }
        }
    }
}