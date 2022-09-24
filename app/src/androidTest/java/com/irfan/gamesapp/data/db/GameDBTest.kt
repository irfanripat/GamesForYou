package com.irfan.gamesapp.data.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.irfan.gamesapp.data.db.dao.GameDao
import com.irfan.gamesapp.data.model.Game
import com.irfan.gamesapp.getOrAwaitValue
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class GameDBTest : TestCase() {

    private lateinit var db: GameDB
    private lateinit var dao: GameDao

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, GameDB::class.java).allowMainThreadQueries()
            .build()
        dao = db.gameDao()
    }

    @After
    public override fun tearDown() {
        db.close()
    }

    @Test
    fun insertAndCheckIfGameIsStoredInDB() = runBlocking {
        val game = Game(1, "Dummy Game", 5.0, "Dummy Description", "Dummy Image")
        dao.insertGame(game)
        val isFavoriteGame = game.id?.let { dao.isFavoriteGame(it) }
        assertThat(isFavoriteGame, `is`(true))
    }

    @Test
    fun insertThenDeleteAndCheckIfGameIsDeletedFromDB(): Unit = runBlocking {
        val game = Game(1, "Dummy Game", 5.0, "Dummy Description", "Dummy Image")
        dao.insertGame(game)
        var isFavoriteGame = game.id?.let { dao.isFavoriteGame(it) }
        assertThat(isFavoriteGame, `is`(true))
        game.id?.let { dao.deleteGameWithId(it) }
        isFavoriteGame = game.id?.let { dao.isFavoriteGame(it) }
        assertThat(isFavoriteGame, `is`(false))
    }

    @Test
    fun getAllFavoriteGames() = runTest {
        val game1 = Game(1, "Dummy Game 1", 5.0, "Dummy Description", "Dummy Image")
        val game2 = Game(2, "Dummy Game 2", 5.0, "Dummy Description", "Dummy Image")
        val game3 = Game(3, "Dummy Game 3", 5.0, "Dummy Description", "Dummy Image")
        dao.insertGame(game1)
        dao.insertGame(game2)
        dao.insertGame(game3)

        val result = dao.getGames("").getOrAwaitValue()
        assertThat(result.size, `is`(3))
    }
}