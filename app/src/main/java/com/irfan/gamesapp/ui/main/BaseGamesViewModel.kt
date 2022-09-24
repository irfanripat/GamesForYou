package com.irfan.gamesapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.irfan.gamesapp.data.model.Game

open class BaseGamesViewModel : ViewModel() {

    protected val games: MutableLiveData<PagingData<Game>> = MutableLiveData()

    open fun getListOfGame(query: String = "") {}

    fun getGames(): LiveData<PagingData<Game>> = games
}