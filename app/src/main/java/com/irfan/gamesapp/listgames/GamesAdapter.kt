package com.irfan.gamesapp.listgames

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.irfan.gamesapp.R
import com.irfan.gamesapp.databinding.ItemGamesBinding
import com.irfan.gamesapp.model.Game

class GamesAdapter(private val games: List<Game>) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGamesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(games[position])
    }

    override fun getItemCount(): Int = games.size
}

class ViewHolder(private val binding: ItemGamesBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(game: Game) = with(binding) {
        tvTitle.text = game.title
        tvRating.text = game.rating.toString()
        tvReleaseDate.text = itemView.context.getString(
            R.string.text_release_date,
            game.releaseDate
        )
        Glide.with(itemView.context)
            .load(game.imgUrl)
            .into(imgBackground)
    }
}