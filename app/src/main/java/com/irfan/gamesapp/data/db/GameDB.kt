package com.irfan.gamesapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.irfan.gamesapp.data.db.dao.GameDao
import com.irfan.gamesapp.data.model.Game

@Database(entities = [Game::class], version = 1, exportSchema = false)
abstract class GameDB : RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        @Volatile
        private var INSTANCE: GameDB? = null

        fun getDatabase(context: Context): GameDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameDB::class.java,
                    "game_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}