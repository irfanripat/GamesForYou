package com.irfan.gamesapp.ui.main.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.irfan.gamesapp.databinding.FragmentGamesBinding
import com.irfan.gamesapp.ui.detail.DetailActivity
import kotlinx.coroutines.launch

class GamesFragment(private val type: TYPE) : Fragment() {

    private var _binding: FragmentGamesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GamesViewModel by viewModels()

    private val adapter by lazy {
        GamesAdapter(
            onItemClicked = {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, it)
                startActivity(intent)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getGames(type)
        binding.rvGames.adapter = adapter
        viewModel.games.observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

enum class TYPE {
    HOME, FAVORITE
}