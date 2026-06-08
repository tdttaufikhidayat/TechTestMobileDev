package com.taufikhidayat.techtestmobiledev.presentation.viewmodel

import app.cash.turbine.test
import com.taufikhidayat.techtestmobiledev.data.remote.dto.SourceDto
import com.taufikhidayat.techtestmobiledev.domain.repository.NewsRepository
import com.taufikhidayat.techtestmobiledev.presentation.state.UiState
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SourceViewModelTest {

    private val repository = mockk<NewsRepository>()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchSources should emit Success state when repository returns data`() = runTest {
        // Arrange
        val mockSources =
            listOf(SourceDto(id = "bbc", name = "BBC News", description = null, category = null))
        coEvery { repository.getSources(any()) } returns mockSources

        val viewModel = SourceViewModel(repository)

        // Act & Assert
        viewModel.sourcesState.test {
            // Skip initial state (Loading)
            awaitItem()

            // Verifikasi state sukses
            val successState = awaitItem() as UiState.Success
            Assert.assertEquals(mockSources, successState.data)
        }
    }

    @Test
    fun `fetchSources should emit Error state when repository throws exception`() = runTest {

        val message = "Tidak ada koneksi internet. Periksa kembali jaringan Anda."
        // Arrange
        coEvery { repository.getSources(any()) } throws Exception(message)

        val viewModel = SourceViewModel(repository)

        // Act & Assert
        viewModel.sourcesState.test {
            awaitItem() // Loading

            val errorState = awaitItem() as UiState.Error
            Assert.assertEquals(
                message,
                errorState.message
            )
        }
    }

    @Test
    fun `fetchSources should filter sources based on query`() = runTest {
        // Arrange
        val mockSources = listOf(
            SourceDto(id = "1", name = "BBC News", description = null, category = null),
            SourceDto(id = "2", name = "CNN", description = null, category = null)
        )
        coEvery { repository.getSources(any()) } returns mockSources

        val viewModel = SourceViewModel(repository)

        // Act
        viewModel.setSearchQuery("BBC") // Melakukan pencarian "BBC"

        // Assert
        viewModel.sourcesState.test {
            awaitItem() // Skip Loading

            // Verifikasi state sukses dan isinya hanya BBC
            val successState = awaitItem() as UiState.Success
            assertEquals(1, successState.data.size)
            assertEquals("BBC News", successState.data[0].name)
        }
    }
}