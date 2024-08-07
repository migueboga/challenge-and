package com.op.storage.data.repository

import com.op.storage.data.database.dao.ProfileDao
import com.op.storage.data.database.entity.Profile
import com.op.storage.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileRoomRepository @Inject constructor(
    private val profileDao: ProfileDao
): ProfileRepository {

    override fun getProfile(): Flow<Profile> = profileDao.get()

    override suspend fun setProfile(profile: Profile) = profileDao.insert(profile)


}