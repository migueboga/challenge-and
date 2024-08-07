package com.op.storage.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.op.storage.data.database.entity.TopRatedMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface TopRatedMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(topRatedMovieList: List<TopRatedMovie>)

    @Query("SELECT * FROM topratedmovie")
    fun get(): Flow<List<TopRatedMovie>>

}