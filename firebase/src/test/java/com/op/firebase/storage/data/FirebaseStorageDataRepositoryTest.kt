package com.op.firebase.storage.data

import com.google.firebase.storage.StorageReference
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.After
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FirebaseStorageDataRepositoryTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var repository: FirebaseStorageDataRepository
    private lateinit var storageReference: StorageReference

    @Before
    fun setUp() {
        storageReference = mockk()
        repository = FirebaseStorageDataRepository(storageReference, testDispatcher)
    }


    @Test
    fun downloadAllPhotos() {
        // Given
        val expectedFile = mockk<StorageReference>()
        val expectedPhotoReferences = listOf(mockk<StorageReference>(), mockk<StorageReference>())
        val expectedFlow = flowOf(expectedPhotoReferences)
        every { storageReference.child(any()) } returns expectedFile
        coEvery { expectedFile.listAll().await().items } returns expectedPhotoReferences

        // When
        val result = repository.downloadAllPhotos()

        // Then
        assertEquals(expectedFlow, result)

    }

}