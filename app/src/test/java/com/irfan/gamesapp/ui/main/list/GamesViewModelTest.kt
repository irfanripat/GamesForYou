package com.irfan.gamesapp.ui.main.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import com.irfan.gamesapp.NoopListCallback
import com.irfan.gamesapp.data.repository.GameRepository
import com.irfan.gamesapp.dummyGame
import com.irfan.gamesapp.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class GamesViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val gameRepository = mock(GameRepository::class.java)
    private val viewModel = GamesViewModel(gameRepository)

    private val testScope = TestScope()
    private val testDispatcher = StandardTestDispatcher(testScope.testScheduler)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test when get list of games from api then it should have the same data to be stored into livedata`() =
        runTest {
            val expected = listOf(dummyGame, dummyGame, dummyGame)
            whenever(gameRepository.getAllGames("")).thenReturn(flow {
                emit(
                    PagingData.from(expected)
                )
            })

            viewModel.getGames(TYPE.HOME, "")
            val result = viewModel.games.getOrAwaitValue()

            val differ = AsyncPagingDataDiffer(
                GameComparator,
                NoopListCallback(),
                Dispatchers.Main
            )

            differ.submitData(result)
            assertEquals(expected.size, differ.itemCount)
        }

    @Test
    fun `test when get list of games from local DB then it should have the same data to be stored into livedata`() = runTest {
        val expected = listOf(dummyGame, dummyGame, dummyGame)
        `when`(gameRepository.getAllFavoriteGames("")).thenReturn(MutableLiveData(expected))

        viewModel.getGames(TYPE.FAVORITE, "")

        viewModel.games.observeForever {
            val differ = AsyncPagingDataDiffer(
                GameComparator,
                NoopListCallback(),
                Dispatchers.Main
            )

            testScope.launch {
                differ.submitData(it)
                assertEquals(expected.size, differ.itemCount)
            }
        }
    }
}