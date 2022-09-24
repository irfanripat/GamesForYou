package com.irfan.gamesapp.ui.main.home

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.irfan.gamesapp.data.repository.GameRepository
import com.irfan.gamesapp.ui.main.BaseGamesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GamesViewModel(private val repository: GameRepository) : BaseGamesViewModel() {

    override fun getListOfGame(query: String) {
        viewModelScope.launch {
            repository.getAllGames(query).cachedIn(this).collectLatest {
                games.postValue(it)
            }
        }
    }
}