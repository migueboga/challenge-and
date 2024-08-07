package com.op.storage.domain.usecase

import com.op.storage.data.repository.ProfileRoomRepository
import javax.inject.Inject

class GetProfileLocalUseCase @Inject constructor(
    private val profileRepository: ProfileRoomRepository
) {

    operator fun invoke() = profileRepository.getProfile()

}