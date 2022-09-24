package com.irfan.gamesapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.irfan.gamesapp.databinding.FragmentGamesBinding
import com.irfan.gamesapp.ui.detail.DetailActivity
import com.irfan.gamesapp.utils.EspressoIdlingResource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseGamesFragment : Fragment() {

    private var _binding: FragmentGamesBinding? = null
    private val binding get() = _binding!!

    val viewModel: BaseGamesViewModel by lazy {
        initViewModel()
    }

    abstract fun initViewModel(): BaseGamesViewModel

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
        EspressoIdlingResource.increment()
        viewModel.getListOfGame()
        initListener()
        observeData()
    }

    private fun initListener() = with(binding) {
        svGames.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {

                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.getListOfGame(it)
                }
                return true
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->
                progressBar.isVisible = loadStates.refresh is LoadState.Loading
                rvGames.isVisible = loadStates.refresh is LoadState.NotLoading
                if (loadStates.refresh is LoadState.NotLoading && adapter.itemCount > 0 || loadStates.refresh is LoadState.Error) {
                    EspressoIdlingResource.decrement()
                }
            }
        }

        swipeRefresh.setOnRefreshListener {
            viewModel.getListOfGame()
            swipeRefresh.isRefreshing = false
        }

        rvGames.adapter = adapter
    }

    private fun observeData() {
        viewModel.getGames().observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}