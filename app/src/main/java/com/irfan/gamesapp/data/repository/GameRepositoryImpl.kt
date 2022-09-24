package com.irfan.gamesapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.irfan.gamesapp.data.db.dao.GameDao
import com.irfan.gamesapp.data.model.Game
import com.irfan.gamesapp.data.paging.datasource.GameDataSource
import com.irfan.gamesapp.data.service.RetrofitHelper
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GameRepositoryImpl(private val dao: GameDao) : GameRepository {

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

    override fun getAllFavoriteGames(query: String): LiveData<List<Game>> {
        return dao.getFavoriteGames(query)
    }

    override suspend fun addFavoriteGame(game: Game) {
        dao.insertGame(game)
    }

    override suspend fun deleteFavoriteGame(id: Int) {
        dao.deleteGameWithId(id)
    }

    override fun isFavoriteGame(id: Int): Boolean {
        return dao.isFavoriteGame(id)
    }
}