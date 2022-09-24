package com.irfan.gamesapp.ui.main.home

import com.irfan.gamesapp.data.db.GameDB
import com.irfan.gamesapp.data.repository.GameRepositoryImpl
import com.irfan.gamesapp.ui.main.BaseGamesFragment
import com.irfan.gamesapp.ui.main.BaseGamesViewModel

class GamesFragment : BaseGamesFragment() {

    override fun initViewModel(): BaseGamesViewModel {
        return GamesViewModel(GameRepositoryImpl(GameDB.getDatabase(requireContext()).gameDao()))
    }
}