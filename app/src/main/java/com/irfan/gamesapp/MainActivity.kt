package com.irfan.gamesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.irfan.gamesapp.databinding.ActivityMainBinding
import com.irfan.gamesapp.listgames.GamesFragment
import com.irfan.gamesapp.listgames.TYPE

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setCurrentFragment(GamesFragment(TYPE.HOME))
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    setCurrentFragment(GamesFragment(TYPE.HOME))
                    supportActionBar?.title = "Games For You"
                }
                R.id.favorite -> {
                    setCurrentFragment(GamesFragment(TYPE.FAVORITE))
                    supportActionBar?.title = "Favorite Games"
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