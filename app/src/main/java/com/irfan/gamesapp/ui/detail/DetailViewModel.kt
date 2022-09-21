package com.irfan.gamesapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irfan.gamesapp.data.model.Game
import com.irfan.gamesapp.data.repository.GameRepository
import com.irfan.gamesapp.data.repository.GameRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: GameRepository = GameRepositoryImpl.getInstance()
) : ViewModel() {

    private val _game = MutableLiveData<Game>()
    val game: LiveData<Game> = _game

    fun getDetailGame(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getDetailGame(id)
            if (response.isSuccessful) {
                _game.postValue(response.body())
            }
        }
    }
}