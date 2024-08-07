package com.op.storage.domain.repository

import com.op.storage.data.database.entity.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getProfile(): Flow<Profile>

    suspend fun setProfile(profile: Profile)

}