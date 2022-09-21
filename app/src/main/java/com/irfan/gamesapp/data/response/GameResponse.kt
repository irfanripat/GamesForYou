package com.irfan.gamesapp.data.response

import com.irfan.gamesapp.data.model.Game

data class GameResponse(
    val count: Int?,
    val next: String?,
    val previous: String?,
    val results: List<Game>?
)
