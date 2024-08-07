package com.op.movies.presentation.profile

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.op.movies.databinding.FragmentProfileBinding
import com.op.movies.util.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var reviewAdapter: ReviewAdapter
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        reviewAdapter = ReviewAdapter()
        return root
    }

    override fun onStart() {
        super.onStart()
        attachAdapter()
        collectUiState()
        viewModel.getPopular()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun collectUiState() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            with(binding) {
                profileNameValue.text = uiState.name
                profilePopularityValue.text = uiState.popularity
                profileIdValue.text = uiState.id
                profileImage.loadImage(uiState.profilePath, requireContext())
                reviewAdapter.update(uiState.reviews)
            }
        }
    }

    private fun attachAdapter() {
        binding.profileRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = reviewAdapter
        }
    }

}