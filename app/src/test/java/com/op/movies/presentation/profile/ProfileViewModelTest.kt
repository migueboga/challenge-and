package com.op.movies.presentation.profile


import com.op.moviedb.domain.entity.Person
import com.op.moviedb.domain.usecase.GetPopularUseCase
import com.op.movies.MainDispatcherRule
import com.op.storage.data.database.entity.Profile
import com.op.storage.domain.usecase.GetProfileLocalUseCase
import com.op.storage.domain.usecase.SetProfileLocalUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {


    @get:Rule
    val coroutinesTestRule = MainDispatcherRule()

    @RelaxedMockK
    private lateinit var getPopularUseCase: GetPopularUseCase

    @RelaxedMockK
    private lateinit var getProfileLocalUseCase: GetProfileLocalUseCase

    @RelaxedMockK
    private lateinit var setProfileLocalUseCase: SetProfileLocalUseCase

    private lateinit var viewModel: ProfileViewModel

    private val profile = Profile(
        2,
        "Mike",
        "path",
        "32432.32432",
        listOf()
    )
    private val person = Person(
        true,
        2,
        2,
        "202",
        "Mike",
        "Miguel",
        32432.32432,
        "path",
        listOf()
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = ProfileViewModel(
            getPopularUseCase,
            getProfileLocalUseCase,
            setProfileLocalUseCase
        )
    }

    @Test
    fun `when get Popular and only response the local data source then set profile to uiState`() {
        // Given
        coEvery { getProfileLocalUseCase() } returns flowOf(profile)
        coEvery { getPopularUseCase() } returns flowOf()

        // When
        viewModel.getPopular()


        // Then
        verify(exactly = 1) { getProfileLocalUseCase() }
        verify(exactly = 1) { getPopularUseCase() }
        assertEquals(
            profile.id.toString(), viewModel.uiState.value?.id
        )
        assertEquals(
            profile.name, viewModel.uiState.value?.name
        )
        assertEquals(
            profile.profilePath, viewModel.uiState.value?.profilePath
        )
    }

    @Test
    fun `when get Popular and response remote data source then set profile to uiState`() {
        // Given
        every { getProfileLocalUseCase() } returns flowOf(null)
        every { getPopularUseCase() } returns flowOf(person)
        coEvery { setProfileLocalUseCase(profile) } returns Unit

        // When
        viewModel.getPopular()


        // Then
        verify(exactly = 1) { getProfileLocalUseCase() }
        verify(exactly = 1) { getPopularUseCase() }
        coVerify(exactly = 1) { setProfileLocalUseCase(profile) }
        assertEquals(
            profile.id.toString(), viewModel.uiState.value?.id
        )
        assertEquals(
            profile.name, viewModel.uiState.value?.name
        )
        assertEquals(
            profile.profilePath, viewModel.uiState.value?.profilePath
        )
    }

}