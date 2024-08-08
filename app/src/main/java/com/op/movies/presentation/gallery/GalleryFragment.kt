package com.op.movies.presentation.gallery

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.op.movies.R
import com.op.movies.databinding.FragmentGalleryBinding
import com.op.movies.presentation.base.BaseFragment
import com.op.movies.presentation.dialog.GenericDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : BaseFragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val viewModel: GalleryViewModel by viewModels()
    private val binding get() = _binding!!
    lateinit var galleryAdapter: GalleryAdapter

    companion object {
        private const val READ_EXTERNAL_STORAGE_PERMISSION_CODE = 99
        private const val OPEN_GALLERY_CODE = 88
        private const val TAG_ERROR_DIALOG = "tagGenericError"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        galleryAdapter = GalleryAdapter()
        return root
    }

    override fun onStart() {
        super.onStart()
        attachGalleryAdapter()
        viewModel.requestImages()
        collectUiState()
        binding.galleryFab.setOnClickListener {
            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPhotoPermissionForTiramisuOrLater()
            } else {
                requestPhotoPermissionLegacy()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            READ_EXTERNAL_STORAGE_PERMISSION_CODE -> {
                if (
                    grantResults.isNotEmpty() &&
                    (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    startGalleryActivityForResult()
                } else {
                    //TODO alert here. permission not granted
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            OPEN_GALLERY_CODE -> {
                if (resultCode == RESULT_OK) {
                    data?.data?.let { uri ->
                        viewModel.uploadImage(uri, requireContext())
                    }
                }
            }
        }
    }

    override fun collectUiState() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            galleryAdapter.update(uiState.imageUriList)
            if (uiState.error != null) {
                showErrorDialog(uiState.error)
                viewModel.clearError()
            }
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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestPhotoPermissionForTiramisuOrLater() {
        if (
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_MEDIA_IMAGES
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                READ_EXTERNAL_STORAGE_PERMISSION_CODE
            )
        } else {
            startGalleryActivityForResult()
        }
    }

    private fun requestPhotoPermissionLegacy() {
        if (
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_EXTERNAL_STORAGE_PERMISSION_CODE
            )
        } else {
            startGalleryActivityForResult()
        }
    }

    private fun getGalleryIntent() = Intent(
        Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI
    ).apply {
        putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpg"))
    }

    private fun startGalleryActivityForResult() {
        startActivityForResult(
            getGalleryIntent(),
            OPEN_GALLERY_CODE
        )
    }

    private fun attachGalleryAdapter() {
        binding.galleryRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = galleryAdapter
        }
    }
}