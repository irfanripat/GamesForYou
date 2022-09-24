package com.irfan.gamesapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.irfan.gamesapp.R
import com.irfan.gamesapp.databinding.ActivityMainBinding
import com.irfan.gamesapp.ui.main.favorite.FavoriteGamesFragment
import com.irfan.gamesapp.ui.main.home.GamesFragment

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setCurrentFragment(GamesFragment())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    setCurrentFragment(GamesFragment())
                    supportActionBar?.title = getString(R.string.title_home_page)
                }
                R.id.favorite -> {
                    setCurrentFragment(FavoriteGamesFragment())
                    supportActionBar?.title = getString(R.string.title_favorite_game_page)
                }
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    }
}