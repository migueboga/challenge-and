package com.op.storage.domain.usecase

import com.op.storage.data.database.entity.Profile
import com.op.storage.data.repository.ProfileRoomRepository
import javax.inject.Inject

class SetProfileLocalUseCase @Inject constructor(
    private val profileRepository: ProfileRoomRepository
) {

    suspend operator fun invoke(
        profile: Profile
    ) = profileRepository.setProfile(profile)

}