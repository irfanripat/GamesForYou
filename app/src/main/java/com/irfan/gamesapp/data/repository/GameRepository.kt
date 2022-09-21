package com.irfan.gamesapp.data.repository

import androidx.paging.PagingData
import com.irfan.gamesapp.data.model.Game
import kotlinx.coroutines.flow.Flow

interface GameRepository {

    fun getAllGames() : Flow<PagingData<Game>>

}