package com.irfan.gamesapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.irfan.gamesapp.data.model.Game


@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(games: Game)

    @Query("DELETE FROM games WHERE id = :id")
    suspend fun deleteGameWithId(id: Int)

    @Query("SELECT * FROM games where name LIKE '%' || :searchQuery || '%'")
    fun getFavoriteGames(searchQuery: String): LiveData<List<Game>>

    @Query("SELECT EXISTS(SELECT * FROM games WHERE id = :id)")
    fun isFavoriteGame(id: Int): Boolean
}