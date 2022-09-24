package com.irfan.gamesapp.ui.main.favorite

import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.irfan.gamesapp.data.repository.GameRepository
import com.irfan.gamesapp.ui.main.BaseGamesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoriteGamesViewModel(private val repository: GameRepository) : BaseGamesViewModel() {

    override fun getListOfGame(query: String) {
        viewModelScope.launch {
            repository.getAllFavoriteGames(query).asFlow().collectLatest {
                games.postValue(PagingData.from(it))
            }
        }
    }
}