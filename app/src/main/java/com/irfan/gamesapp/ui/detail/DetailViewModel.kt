package com.irfan.gamesapp.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.irfan.gamesapp.data.db.GameDB
import com.irfan.gamesapp.data.model.Game
import com.irfan.gamesapp.data.repository.GameRepository
import com.irfan.gamesapp.data.repository.GameRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GameRepository
    private val gameDB = GameDB.getDatabase(application)

    init {
        repository = GameRepositoryImpl(gameDB)
    }

    private val _game = MutableLiveData<Game>()
    val game: LiveData<Game> = _game

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun setId(id: Int) {
        getDetailGame(id)
        checkIfFavorite(id)
    }

    private fun getDetailGame(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        val response = repository.getDetailGame(id)
        if (response.isSuccessful) {
            _game.postValue(response.body())
        }
    }

    private fun checkIfFavorite(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        val result = gameDB.gameDao().isFavoriteGame(id)
        _isFavorite.postValue(result)
    }

    private fun addToFavorite() = viewModelScope.launch(Dispatchers.IO) {
        game.value?.let { repository.addFavoriteGame(it) }
        checkIfFavorite(game.value?.id ?: 0)
    }

    private fun deleteFromFavorite() = viewModelScope.launch(Dispatchers.IO) {
        game.value?.id?.let { repository.deleteFavoriteGame(it) }
        checkIfFavorite(game.value?.id ?: 0)
    }

    fun onFavoriteClicked() {
        if (isFavorite.value == true) {
            deleteFromFavorite()
        } else {
            addToFavorite()
        }
    }
}