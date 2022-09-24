package com.irfan.gamesapp.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.irfan.gamesapp.data.repository.GameRepository
import com.irfan.gamesapp.dummyGame
import com.irfan.gamesapp.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import retrofit2.Response

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val gameRepository = Mockito.mock(GameRepository::class.java)
    private val viewModel = DetailViewModel(gameRepository)

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
    fun `check if game is favorite then the return should be true`() {
        val movieId = 1
        Mockito.`when`(gameRepository.isFavoriteGame(movieId)).thenReturn(true)

        viewModel.checkIfFavorite(movieId)

        viewModel.isFavorite.observeForever {
            assert(it == true)
        }
    }

    @Test
    fun `check if game is not favorite then the return should be false`() {
        val movieId = 1
        Mockito.`when`(gameRepository.isFavoriteGame(movieId)).thenReturn(false)

        viewModel.checkIfFavorite(movieId)

        viewModel.isFavorite.observeForever {
            assert(it == false)
        }
    }

    @Test
    fun `test when get detail of game from api then it should be stored in livedata`() =
        testScope.runTest {
            val gameId = 1
            Mockito.`when`(gameRepository.getDetailGame(gameId))
                .thenReturn(Response.success(dummyGame.copy(id = gameId)))

            viewModel.getDetailGame(gameId)
            advanceUntilIdle()
            val result = viewModel.game.getOrAwaitValue()

            assertEquals(result.id, gameId)
            assertEquals(result.name, dummyGame.name)
        }
}