package com.licoding.instagramapp.domain.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.licoding.instagramapp.data.models.User
import com.licoding.instagramapp.data.models.UserResult


@Database(
    entities = [User::class, UserResult::class],
    version = 4,
    exportSchema = false
)
abstract class InstagramDatabase: RoomDatabase() {
    abstract val userDao: UserDao
    abstract val resultRecentSearchDao: RecentSearchDao
}

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE user ADD COLUMN name TEXT")
    }
}
val MIGRATION_3_4: Migration = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE user ADD COLUMN followerCount BIGINT DEFAULT 0")
        db.execSQL("ALTER TABLE user ADD COLUMN followingCount BIGINT DEFAULT 0")
        db.execSQL("ALTER TABLE user ADD COLUMN postCount BIGINT DEFAULT 0")
    }
}

val MIGRATION_2_3: Migration = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS `userresult` (" +
                "`id` TEXT NOT NULL, " +
                "`username` TEXT NOT NULL, " +
                "`profileImage` TEXT, " +
                "`name` TEXT, " +
                "PRIMARY KEY(`id`))")
    }
}


//val MIGRATION_2_3: Migration = object : Migration(2, 3) {
//    override fun migrate(db: SupportSQLiteDatabase) {
//        // create comic bookmark table
//        db.execSQL("CREATE TABLE IF NOT EXISTS `userresult` (`id` TEXT NOT NULL, `username` TEXT NOT NULL, `profileImage` TEXT NOT NULL, `name` TEXT, PRIMARY KEY(`id`))")
//    }
//}