package com.irfan.gamesapp.ui.detail

import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.irfan.gamesapp.R
import com.irfan.gamesapp.data.db.GameDB
import com.irfan.gamesapp.data.model.Game
import com.irfan.gamesapp.data.repository.GameRepositoryImpl
import com.irfan.gamesapp.databinding.ActivityDetailBinding
import com.irfan.gamesapp.di.viewModelBuilder

class DetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val viewModel: DetailViewModel by viewModelBuilder {
        DetailViewModel(
            repository = GameRepositoryImpl(
                GameDB.getDatabase(applicationContext).gameDao()
            )
        )
    }

    private var menuItem: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
        intent.extras?.getInt(EXTRA_ID)?.let {
            viewModel.run {
                getDetailGame(it)
                checkIfFavorite(it)
            }
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
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_filled_white)
        } else {
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white)
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
            renderFavoriteState(it)
        }
        viewModel.isLoading.observe(this) {
            binding.progressBar.isVisible = it
            binding.clContainer.isVisible = !it
        }
    }

    private fun renderDetailMovie(game: Game) = with(binding) {
        game.let {
            tvTitle.text = it.name
            tvRating.text = it.rating.toString()
            tvReleaseDate.text = getString(R.string.text_release_date, it.released)
            tvPlayTime.text = getString(R.string.text_playtime, it.playtime)
            tvDeveloper.text = it.developers?.getOrNull(0)?.name
            tvDescription.text = Html.fromHtml(it.description)
            Glide.with(this@DetailActivity)
                .load(it.backgroundImage)
                .placeholder(R.drawable.img_placeholder)
                .into(ivBackground)
        }
    }

    companion object {
        const val EXTRA_ID = "extraId"
    }
}