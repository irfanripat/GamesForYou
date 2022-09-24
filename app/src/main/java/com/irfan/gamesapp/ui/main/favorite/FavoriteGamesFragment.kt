package com.irfan.gamesapp.ui.main.favorite

import com.irfan.gamesapp.data.db.GameDB
import com.irfan.gamesapp.data.repository.GameRepositoryImpl
import com.irfan.gamesapp.ui.main.BaseGamesFragment
import com.irfan.gamesapp.ui.main.BaseGamesViewModel

class FavoriteGamesFragment : BaseGamesFragment() {

    override fun initViewModel(): BaseGamesViewModel {
        return FavoriteGamesViewModel(GameRepositoryImpl(GameDB.getDatabase(requireContext()).gameDao()))
    }
}