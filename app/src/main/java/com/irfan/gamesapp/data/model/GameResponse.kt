package com.irfan.gamesapp.data.model

data class GameResponse(
    val count: Int?,
    val next: String?,
    val previous: String?,
    val results: List<Game>?
)
