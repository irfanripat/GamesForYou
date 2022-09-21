package com.irfan.gamesapp.data.repository

import androidx.paging.PagingData
import com.irfan.gamesapp.data.model.Game

interface GameRepository {

    fun getAllGames() : PagingData<Game>


}