package com.irfan.gamesapp.listgames

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.irfan.gamesapp.model.Game

class GamesViewModel : ViewModel() {

    private val _games = MutableLiveData<List<Game>>()
    val games: MutableLiveData<List<Game>> = _games

    fun getGames(type: TYPE) {
        _games.value = when (type) {
            TYPE.HOME -> dummyGames
            TYPE.FAVORITE -> dummyFavGames
        }
    }
}

val dummyGames = listOf(
    Game("Game 1", 4.8, "21-08-2020", "https://picsum.photos/200/300"),
    Game("Game 2", 4.7, "22-08-2020", "https://picsum.photos/200/300"),
    Game("Game 3", 4.8, "21-08-2020", "https://picsum.photos/200/300"),
    Game("Game 4", 4.7, "22-08-2020", "https://picsum.photos/200/300"),
    Game("Game 5", 4.8, "21-08-2020", "https://picsum.photos/200/300"),
    Game("Game 6", 4.7, "22-08-2020", "https://picsum.photos/200/300"),
)

val dummyFavGames = listOf(
    Game("Game Fav 1", 4.8, "21-08-2020", "https://picsum.photos/200/300"),
    Game("Game Fav 2", 4.7, "22-08-2020", "https://picsum.photos/200/300"),
    Game("Game Fav 3", 4.8, "21-08-2020", "https://picsum.photos/200/300"),
)