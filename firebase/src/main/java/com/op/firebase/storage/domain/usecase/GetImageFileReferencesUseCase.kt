package com.op.firebase.storage.domain.usecase

import com.op.firebase.storage.data.FirebaseStorageDataRepository
import javax.inject.Inject

class GetImageFileReferencesUseCase @Inject constructor(
    private val firebaseStorageDataRepository: FirebaseStorageDataRepository
) {

    operator fun invoke() = firebaseStorageDataRepository.downloadAllPhotos()

}