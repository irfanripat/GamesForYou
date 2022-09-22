package com.irfan.gamesapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.irfan.gamesapp.data.db.GameDB
import com.irfan.gamesapp.data.model.Game
import com.irfan.gamesapp.data.paging.datasource.GameDataSource
import com.irfan.gamesapp.data.service.RetrofitHelper
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GameRepositoryImpl(private val db: GameDB) : GameRepository {

    override suspend fun getAllGames(query: String): Flow<PagingData<Game>> = Pager(
        config = PagingConfig(
            pageSize = 15,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { GameDataSource(
            RetrofitHelper.apiService,
            query
        ) }
    ).flow

    override suspend fun getDetailGame(id: Int): Response<Game> {
        return RetrofitHelper.apiService.getDetailGame(id)
    }

    override suspend fun getAllFavoriteGames(query: String): LiveData<List<Game>> {
        return db.gameDao().getGames(query)
    }

    override suspend fun addFavoriteGame(game: Game) {
        db.gameDao().insertGame(game)
    }

    override suspend fun deleteFavoriteGame(id: Int) {
        db.gameDao().deleteGameWithId(id)
    }

    override fun isFavoriteGame(id: Int): Boolean {
        return db.gameDao().isFavoriteGame(id)
    }

    companion object {
        private var INSTANCE: GameRepositoryImpl? = null

        fun getInstance(db: GameDB): GameRepositoryImpl {
            if (INSTANCE == null) {
                INSTANCE = GameRepositoryImpl(db)
            }
            return INSTANCE!!
        }
    }
}