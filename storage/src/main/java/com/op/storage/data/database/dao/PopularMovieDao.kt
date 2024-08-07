package com.op.storage.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.op.storage.data.database.entity.PopularMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface PopularMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(popularMovieList: List<PopularMovie>)

    @Query("SELECT * FROM popularmovie")
    fun get(): Flow<List<PopularMovie>>



}