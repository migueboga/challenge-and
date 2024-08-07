package com.op.movies.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.op.movies.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var popularAdapter: MovieAdapter
    private lateinit var topRatedAdapter: MovieAdapter
    private lateinit var recommendationsAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        popularAdapter = MovieAdapter()
        topRatedAdapter = MovieAdapter()
        recommendationsAdapter = MovieAdapter()
        return root
    }

    override fun onStart() {
        super.onStart()
        attachPopularAdapter()
        attachTopRatedAdapter()
        attachRecommendationsAdapter()
        collectUiState()
        viewModel.getPopularMovies()
        viewModel.getTopRatedMovies()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun collectUiState() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            popularAdapter.update(uiState.popularMovieList)
            topRatedAdapter.update(uiState.topRatedMovieList)
            recommendationsAdapter.update(uiState.recommendationList)
        }
    }

    private fun attachPopularAdapter() {
        binding.homePopularRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = popularAdapter
        }
    }

    private fun attachTopRatedAdapter() {
        binding.homeRatedRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = topRatedAdapter
        }
    }

    private fun attachRecommendationsAdapter() {
        binding.homeRecomendedRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = recommendationsAdapter
        }
    }
}