package com.op.firebase.storage.domain.usecase

import com.op.firebase.storage.data.FirebaseStorageDataRepository
import java.io.File
import javax.inject.Inject

class UploadImageFileUseCase @Inject constructor(
    private val firebaseStorageDataRepository: FirebaseStorageDataRepository
){

    operator fun invoke(imageFile: File) = firebaseStorageDataRepository.upload(imageFile)

}