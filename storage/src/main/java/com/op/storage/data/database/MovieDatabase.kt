package com.op.storage.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [], version = 1
)
abstract class MovieDatabase: RoomDatabase() {

    companion object {
        internal const val DATABASE_NAME = "movie_db"
    }
}