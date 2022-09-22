package com.irfan.gamesapp.ui.main.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.irfan.gamesapp.data.db.GameDB
import com.irfan.gamesapp.data.model.Game
import com.irfan.gamesapp.data.repository.GameRepository
import com.irfan.gamesapp.data.repository.GameRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GamesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GameRepository =
        GameRepositoryImpl.getInstance(GameDB.getDatabase(application))

    private val _games = MutableLiveData<PagingData<Game>>()
    val games: LiveData<PagingData<Game>> = _games

    fun getGames(type: TYPE, query: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            if (type == TYPE.HOME) {
                repository.getAllGames(query).cachedIn(this).collectLatest {
                    _games.postValue(it)
                }
            } else {
                repository.getAllFavoriteGames(query)?.asFlow()?.collectLatest {
                    _games.postValue(PagingData.from(it))
                }
            }
        }
    }
}