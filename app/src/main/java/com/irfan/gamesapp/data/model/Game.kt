package com.irfan.gamesapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class Game(
    @PrimaryKey val id: Int?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "rating") val rating: Double?,
    @ColumnInfo(name = "released") val released: String?,
    @ColumnInfo(name = "background_image") val backgroundImage: String?,
    @Ignore val description: String?,
    @Ignore val description_raw: String?,
    @Ignore val developers: List<Developer>?,
    @Ignore val playtime: Int? = 0,
) {
    constructor(id: Int, name: String, rating: Double, released: String, backgroundImage: String) : this(
        id,
        name,
        rating,
        released,
        backgroundImage,
        null,
        null,
        null,
        null
    )
}