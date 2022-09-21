package com.irfan.gamesapp.ui.main.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.irfan.gamesapp.R
import com.irfan.gamesapp.data.model.Game
import com.irfan.gamesapp.databinding.ItemGamesBinding

class GamesAdapter(
   private val onItemClicked : (Int) -> Unit
) : PagingDataAdapter<Game, GamesAdapter.ViewHolder>(GameComparator) {



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
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(private val binding: ItemGamesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(game: Game) = with(binding) {
            tvTitle.text = game.name
            tvRating.text = game.rating.toString()
            tvReleaseDate.text = itemView.context.getString(
                R.string.text_release_date,
                game.released
            )
            Glide.with(itemView.context)
                .load(game.backgroundImage)
                .into(imgBackground)

            root.setOnClickListener {
                game.id?.let { onItemClicked.invoke(it) }
            }
        }
    }
}



object GameComparator: DiffUtil.ItemCallback<Game>() {
    override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem == newItem
    }
}