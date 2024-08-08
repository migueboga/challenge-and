package com.op.storage.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.op.storage.domain.entity.Review

@Entity
data class Profile(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val name: String,
    val profilePath: String,
    val popularity: String,
    val reviews: List<Review>
)
