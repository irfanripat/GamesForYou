package com.irfan.gamesapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.irfan.gamesapp.data.model.Game
import com.irfan.gamesapp.data.paging.datasource.GameDataSource
import com.irfan.gamesapp.data.service.RetrofitHelper
import kotlinx.coroutines.flow.Flow

class GameRepositoryImpl : GameRepository {

    override fun getAllGames(): Flow<PagingData<Game>> = Pager(
        config = PagingConfig(
            pageSize = 15,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { GameDataSource(RetrofitHelper.apiService) }
    ).flow

    companion object {
        private var INSTANCE: GameRepositoryImpl? = null

        fun getInstance(): GameRepositoryImpl {
            if (INSTANCE == null) {
                INSTANCE = GameRepositoryImpl()
            }
            return INSTANCE!!
        }
    }
}