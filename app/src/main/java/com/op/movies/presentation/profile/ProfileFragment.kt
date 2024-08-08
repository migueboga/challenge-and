package com.op.movies.presentation.profile

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.op.movies.R
import com.op.movies.databinding.FragmentProfileBinding
import com.op.movies.presentation.base.BaseFragment
import com.op.movies.presentation.dialog.GenericDialogFragment
import com.op.movies.util.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {

    companion object {
        fun newInstance() = ProfileFragment()
        private const val TAG_ERROR_DIALOG = "tagGenericError"
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

    override fun collectUiState() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            with(binding) {
                profileNameValue.text = uiState.name
                profilePopularityValue.text = uiState.popularity
                profileIdValue.text = uiState.id
                profileImage.loadImage(uiState.profilePath, requireContext())
                reviewAdapter.update(uiState.reviews)
            }
            if (uiState.error != null) {
                showErrorDialog(uiState.error)
                viewModel.clearError()
            }
        }
    }

    private fun attachAdapter() {
        binding.profileRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = reviewAdapter
        }
    }

    private fun showErrorDialog(message: Int) {
        GenericDialogFragment(
            R.string.err_generic_title,
            message,
            positiveButtonTitle = R.string.err_generic_positive_button_title,
            onNegativeButtonPressed = {},
            onPositiveButtonPressed = {}
        ).show(parentFragmentManager, TAG_ERROR_DIALOG)
    }

}