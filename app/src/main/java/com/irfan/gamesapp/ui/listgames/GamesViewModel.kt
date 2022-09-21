package com.irfan.gamesapp.ui.listgames

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.irfan.gamesapp.data.model.Game
import com.irfan.gamesapp.data.repository.GameRepository
import com.irfan.gamesapp.data.repository.GameRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GamesViewModel(private val repository: GameRepository = GameRepositoryImpl.getInstance()) :
    ViewModel() {

    private val _games = MutableLiveData<PagingData<Game>>()
    val games: LiveData<PagingData<Game>> = _games

    fun getGames(type: TYPE) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllGames().cachedIn(this).collectLatest {
                _games.postValue(it)
            }
        }
    }
}