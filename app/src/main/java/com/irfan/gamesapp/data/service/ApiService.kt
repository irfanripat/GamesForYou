package com.irfan.gamesapp.data.service

import com.irfan.gamesapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("games")
    fun getGames(
        @Query ("page") page: Int,
        @Query("search") search: String = ""
    )

    @GET("games/{id}")
    fun getDetailGame(
        @Path("id") id: String
    )
}