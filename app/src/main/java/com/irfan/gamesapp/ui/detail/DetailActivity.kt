package com.irfan.gamesapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.irfan.gamesapp.R
import com.irfan.gamesapp.data.model.Game
import com.irfan.gamesapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val viewModel: DetailViewModel by viewModels()
    private var menuItem: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
        intent.extras?.getInt(EXTRA_ID)?.let {
            viewModel.setId(it)
        }
        observeData()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        renderFavoriteState(viewModel.isFavorite.value == true)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
            viewModel.onFavoriteClicked()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderFavoriteState(isFavorite: Boolean) {
        if (isFavorite) {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_filled_white)
        } else {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white)
        }
    }

    private fun initToolbar() {
        binding.toolbar.title = ""
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun observeData() {
        viewModel.game.observe(this) {
            renderDetailMovie(it)
        }
        viewModel.isFavorite.observe(this) {
            Log.d("DetailActivity", "isFavorite: $it")
            renderFavoriteState(it)
        }
    }

    private fun renderDetailMovie(game: Game) = with(binding) {
        tvTitle.text = game.name
        tvRating.text = game.rating.toString()
        tvReleaseDate.text =
            this@DetailActivity.getString(R.string.text_release_date, game.released)
        tvPlayTime.text = this@DetailActivity.getString(R.string.text_playtime, game.playtime)
        tvDeveloper.text = game.developers?.getOrNull(0)?.name
        tvDescription.text = Html.fromHtml(game.description)
        Glide.with(this@DetailActivity)
            .load(game.backgroundImage)
            .into(ivBackground)
    }


    companion object {
        const val EXTRA_ID = "extraId"
    }
}