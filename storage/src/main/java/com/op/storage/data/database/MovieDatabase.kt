package com.op.storage.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.op.storage.data.database.dao.PopularMovieDao
import com.op.storage.data.database.dao.ProfileDao
import com.op.storage.data.database.dao.RecommendMovieDao
import com.op.storage.data.database.dao.TopRatedMovieDao
import com.op.storage.data.database.entity.PopularMovie
import com.op.storage.data.database.entity.Profile
import com.op.storage.data.database.entity.RecommendMovie
import com.op.storage.data.database.entity.TopRatedMovie

@Database(
    entities = [
        PopularMovie::class,
        TopRatedMovie::class,
        RecommendMovie::class,
        Profile::class
               ], version = 6
)
@TypeConverters(Converter::class)
abstract class MovieDatabase: RoomDatabase() {

    companion object {
        internal const val DATABASE_NAME = "movie_db"
    }

    abstract fun popularMovieDao(): PopularMovieDao
    abstract fun topRatedMovieDao(): TopRatedMovieDao
    abstract fun recommendMovieDao(): RecommendMovieDao
    abstract fun profileDao(): ProfileDao

}