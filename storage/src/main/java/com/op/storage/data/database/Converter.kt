package com.op.storage.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.op.storage.domain.entity.Review

class Converter {
    @TypeConverter
    fun fromStringToReview(value: String?): Review? {
        return Gson().fromJson(value, Review::class.java)
    }

    @TypeConverter
    fun fromReviewToString(value: Review?): String? {
        return Gson().toJson(value)
    }
}