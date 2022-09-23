package com.irfan.gamesapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irfan.gamesapp.data.model.Game
import com.irfan.gamesapp.data.repository.GameRepository
import com.irfan.gamesapp.utils.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: GameRepository) : ViewModel() {

    private val _game = MutableLiveData<Game>()
    val game: LiveData<Game> = _game

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun getDetailGame(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        _isLoading.postValue(true)
        val response = repository.getDetailGame(id)
        if (response.isSuccessful) {
            _game.postValue(response.body())
            EspressoIdlingResource.decrement()
        }
        _isLoading.postValue(false)
    }

    fun checkIfFavorite(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        val result = repository.isFavoriteGame(id)
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