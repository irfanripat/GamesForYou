package com.irfan.gamesapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.irfan.gamesapp.R
import com.irfan.gamesapp.data.model.Game
import com.irfan.gamesapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        intent.extras?.getInt(EXTRA_ID)?.let {
            viewModel.getDetailGame(it)
        }
        viewModel.game.observe(this) {
            renderDetailMovie(it)
        }
    }

    private fun renderDetailMovie(game: Game) = with(binding) {
        tvTitle.text = game.name
        tvRating.text = game.rating.toString()
        tvReleaseDate.text = this@DetailActivity.getString(R.string.text_release_date, game.released)
        tvPlayTime.text = game.playtime.toString()
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