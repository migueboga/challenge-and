package com.op.movies.presentation.map

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.op.movies.R
import com.op.movies.databinding.FragmentMapBinding
import com.op.movies.presentation.base.BaseFragment
import com.op.movies.presentation.dialog.GenericDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : BaseFragment(), OnMapReadyCallback {

    companion object {
        fun newInstance() = MapFragment()
        private const val FINE_LOCATION_PERMISSION_CODE = 77
        private const val TAG_ERROR_DIALOG = "tagGenericError"
    }

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MapViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
        return root
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
        requestLocationPermission()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.mapView.onDestroy()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun collectUiState() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            if (uiState.error != null) {
                showErrorDialog(uiState.error)
                viewModel.clearError()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            FINE_LOCATION_PERMISSION_CODE -> {
                if (
                    grantResults.isNotEmpty() &&
                    (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    // TODO: get location
                } else {
                    showPermissionNoGrantedDialog()
                }
            }
        }
    }

    private fun requestLocationPermission() {
        if (
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                FINE_LOCATION_PERMISSION_CODE
            )
        } else {
            // TODO: get location
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

    private fun showPermissionNoGrantedDialog() {
        GenericDialogFragment(
            R.string.err_permission_title,
            R.string.err_require_location_permission,
            positiveButtonTitle = R.string.go_settings,
            negativeButtonTitle = R.string.cancel,
            onPositiveButtonPressed = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", "com.op.movies", null)
                intent.data = uri
                startActivity(intent)
            },
            onNegativeButtonPressed = {}
        )
    }

    override fun onMapReady(map: GoogleMap) {
        val myLocation = LatLng(35.00116, 135.7681)
        map.addMarker(
            MarkerOptions()
                .position(myLocation)
                .title("My posicion")
        )
        map.moveCamera(CameraUpdateFactory.newLatLng(myLocation))
        map.moveCamera(CameraUpdateFactory.zoomTo(15F))
    }
}