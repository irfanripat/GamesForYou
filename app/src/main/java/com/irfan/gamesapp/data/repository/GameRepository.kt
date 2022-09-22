package com.irfan.gamesapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.irfan.gamesapp.data.model.Game
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface GameRepository {

    suspend fun getAllGames() : Flow<PagingData<Game>>

    suspend fun getDetailGame(id: Int) : Response<Game>

    suspend fun getAllFavoriteGames() : LiveData<List<Game>>?

    suspend fun addFavoriteGame(game: Game)

    suspend fun deleteFavoriteGame(id: Int)

    fun isFavoriteGame(id: Int) : LiveData<Boolean>

//    fun getGameById(id: Int) : LiveData<Game>
}