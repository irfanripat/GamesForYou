package com.irfan.gamesapp.data.paging.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.irfan.gamesapp.data.model.Game
import com.irfan.gamesapp.data.service.ApiService

class GameDataSource(
    private val apiService: ApiService,
    private val query: String = ""
) : PagingSource<Int, Game>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Game> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = apiService.getGames(page = nextPageNumber, query)
            val games = response.body()?.results ?: emptyList()
            LoadResult.Page(
                data = games,
                prevKey = if (response.body()?.previous.isNullOrEmpty()) null else nextPageNumber - 1,
                nextKey = if (response.body()?.next.isNullOrEmpty()) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Game>): Int? {
        return state.anchorPosition
    }
}