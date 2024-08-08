package com.op.storage.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.op.storage.data.database.entity.RecommendMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface RecommendMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recommendMovieList: List<RecommendMovie>)

    @Query("SELECT * FROM recommendmovie")
    fun get(): Flow<List<RecommendMovie>>

}