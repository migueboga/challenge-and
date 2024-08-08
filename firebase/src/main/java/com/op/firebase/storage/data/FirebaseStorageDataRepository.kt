package com.op.firebase.storage.data

import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storageMetadata
import com.op.firebase.storage.di.IODispatcher
import com.op.firebase.storage.domain.repository.FirebaseStorageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.io.File
import java.io.FileInputStream
import javax.inject.Inject

class FirebaseStorageDataRepository @Inject constructor(
    private val storageReference: StorageReference,
    @IODispatcher private val dispatcher: CoroutineDispatcher
): FirebaseStorageRepository {

    override fun downloadAllPhotos(): Flow<List<StorageReference>> = flow {
        val storageRef = storageReference.child("/")
        val fileRefs = storageRef.listAll().await().items
        emit(fileRefs)
    }.flowOn(dispatcher)

    override fun upload(
        imageFile: File
    ): Flow<String> = callbackFlow {
        val metaData = storageMetadata {
            contentType = "image/jpeg"
        }
        val path = "${System.currentTimeMillis()}.jpeg"
        val imageReference = storageReference.child(path)
        val stream = FileInputStream(imageFile)
        val uploadTask = imageReference.putStream(stream, metaData)
        uploadTask
            .addOnFailureListener { throw it }
            .addOnSuccessListener {
                trySend(path)
            }
        awaitClose { uploadTask.cancel() }
    }.flowOn(dispatcher)

}