package com.op.firebase.storage.domain.usecase

import com.op.firebase.storage.data.FirebaseStorageDataRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.File

class UploadImageFileUseCaseTest {

    private lateinit var useCase: UploadImageFileUseCase
    private lateinit var repository: FirebaseStorageDataRepository

    @Before
    fun setUp() {
        repository = mockk()
        useCase = UploadImageFileUseCase(repository)
    }

    @Test
    operator fun invoke() {
        // Given
        val mockFile = mockk<File>()
        val expectedResult = flowOf("path")
        every { repository.upload(any()) } returns expectedResult

        // When
        val result = useCase.invoke(mockFile)

        // Then
        verify(exactly = 1) { repository.upload(any()) }
        assertEquals(
            expectedResult,
            result
        )

    }
}