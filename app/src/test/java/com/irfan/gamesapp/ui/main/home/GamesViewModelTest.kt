package com.irfan.gamesapp.ui.main.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import com.irfan.gamesapp.NoopListCallback
import com.irfan.gamesapp.data.repository.GameRepository
import com.irfan.gamesapp.dummyGame
import com.irfan.gamesapp.getOrAwaitValue
import com.irfan.gamesapp.ui.main.BaseGamesViewModel
import com.irfan.gamesapp.ui.main.GameComparator
import com.irfan.gamesapp.ui.main.favorite.FavoriteGamesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
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
    private lateinit var viewModel: BaseGamesViewModel

    private val testScope = TestScope()
    private val testDispatcher = StandardTestDispatcher(testScope.testScheduler)

    private val dummyList = listOf(dummyGame, dummyGame, dummyGame)

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
        testScope.runTest {
            viewModel = GamesViewModel(gameRepository)
            whenever(gameRepository.getAllGames("")).thenReturn(flowOf(PagingData.from(dummyList)))

            viewModel.getListOfGame("")
            advanceUntilIdle()

            val differ = AsyncPagingDataDiffer(
                GameComparator,
                NoopListCallback(),
                Dispatchers.Main
            )
            differ.submitData(viewModel.getGames().getOrAwaitValue())
            advanceUntilIdle()
            assertTrue(dummyList.size == differ.itemCount)

        }

    @Test
    fun `test when get list of games from local DB then it should have the same data to be stored into livedata`() =
        testScope.runTest {
            viewModel = FavoriteGamesViewModel(gameRepository)
            `when`(gameRepository.getAllFavoriteGames("")).thenReturn(MutableLiveData(dummyList))

            viewModel.getListOfGame("")
            advanceUntilIdle()
            val differ = AsyncPagingDataDiffer(
                GameComparator,
                NoopListCallback(),
                Dispatchers.Main
            )

            differ.submitData(viewModel.getGames().getOrAwaitValue())
            advanceUntilIdle()
            assertTrue(dummyList.size == differ.itemCount)
        }
}