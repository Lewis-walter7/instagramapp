package com.licoding.instagramapp.domain.room

import android.content.Context
import androidx.room.Room

object AppDatabaseSingleton {
    private var INSTANCE: InstagramDatabase? = null

    fun getDatabase(context: Context): InstagramDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                InstagramDatabase::class.java,
                "instagram.db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
