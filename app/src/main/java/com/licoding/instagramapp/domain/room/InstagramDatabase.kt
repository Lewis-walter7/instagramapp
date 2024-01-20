package com.licoding.instagramapp.domain.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.licoding.instagramapp.data.models.User


@Database(
    entities = [User::class],
    version = 2,
    exportSchema = false
)
abstract class InstagramDatabase: RoomDatabase() {
    abstract val userDao: UserDao
}

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE user ADD COLUMN name TEXT")
    }
}