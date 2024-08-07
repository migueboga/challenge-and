package com.op.storage.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecommendMovie(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val path: String,
    val title: String
)
