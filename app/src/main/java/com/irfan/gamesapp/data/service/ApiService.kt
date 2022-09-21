package com.irfan.gamesapp.data.service

import com.irfan.gamesapp.BuildConfig
import com.irfan.gamesapp.data.response.GameResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("games")
    suspend fun getGames(
        @Query ("page") page: Int,
        @Query("search") search: String = ""
    ) : Response<GameResponse>

//    @GET("games/{id}")
//    suspend fun getDetailGame(
//        @Path("id") id: String
//    )
}