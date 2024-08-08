package com.op.firebase.storage.domain.repository

import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.Flow
import java.io.File

interface FirebaseStorageRepository {

    fun downloadAllPhotos(): Flow<List<StorageReference>>

    fun upload(
        imageFile: File
    ): Flow<String>

}