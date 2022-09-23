package com.irfan.gamesapp.ui.main.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.irfan.gamesapp.data.model.Game
import com.irfan.gamesapp.data.repository.GameRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GamesViewModel(private val repository: GameRepository) : ViewModel() {

    private val _games = MutableLiveData<PagingData<Game>>()
    val games: LiveData<PagingData<Game>> = _games

    fun getGames(type: TYPE, query: String = "") {
        viewModelScope.launch {
            if (type == TYPE.HOME) {
                repository.getAllGames(query).cachedIn(this).collectLatest {
                    _games.postValue(it)
                }
            } else {
                repository.getAllFavoriteGames(query).asFlow().collectLatest {
                    _games.postValue(PagingData.from(it))
                }
            }
        }
    }
}