package com.irfan.gamesapp.data.model

data class Game(
    val id: Int?,
    val name: String?,
    val rating: Double?,
    val released: String?,
    val backgroundImage: String?,
    val description: String?,
    val description_raw: String?,
    val developers: List<Developer>?,
    val playtime: Int? = 0,
)