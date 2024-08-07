package com.op.storage.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.op.storage.data.database.dao.PopularMovieDao
import com.op.storage.data.database.dao.RecommendMovieDao
import com.op.storage.data.database.dao.TopRatedMovieDao
import com.op.storage.data.database.entity.PopularMovie
import com.op.storage.data.database.entity.RecommendMovie
import com.op.storage.data.database.entity.TopRatedMovie

@Database(
    entities = [
        PopularMovie::class,
    TopRatedMovie::class,
    RecommendMovie::class
               ], version = 3
)
abstract class MovieDatabase: RoomDatabase() {

    companion object {
        internal const val DATABASE_NAME = "movie_db"
    }

    abstract fun popularMovieDao(): PopularMovieDao
    abstract fun topRatedMovieDao(): TopRatedMovieDao
    abstract fun recommendMovieDao(): RecommendMovieDao

}