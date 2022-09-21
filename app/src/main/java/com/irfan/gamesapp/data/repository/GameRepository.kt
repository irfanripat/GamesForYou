package com.irfan.gamesapp.data.repository

import androidx.paging.PagingData
import com.irfan.gamesapp.data.model.Game
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface GameRepository {

    suspend fun getAllGames() : Flow<PagingData<Game>>

    suspend fun getDetailGame(id: Int) : Response<Game>
}