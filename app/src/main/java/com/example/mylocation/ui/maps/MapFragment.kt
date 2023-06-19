package com.example.mylocation.ui.maps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mylocation.R
import com.example.mylocation.data.DatabaseHelper
import com.example.mylocation.databinding.FragmentMapBinding
import com.example.mylocation.ui.contacts.ContactsFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Locale


class MapFragment : Fragment(), OnMapReadyCallback, OnMapClickListener {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private var locationPermissionGranted = false
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.map.onCreate(savedInstanceState)
        binding.map.getMapAsync(this)

    }

    override fun onStart() {
        super.onStart()
        binding.map.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.map.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.map.onStop()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        requestLocationPermission()
        getCurrentLocation()
        mMap.setOnMapClickListener(this)


        mMap.setOnInfoWindowClickListener { marker ->
            databaseHelper = DatabaseHelper(requireContext())

            val title = marker.title.toString()
            val snippet = marker.snippet.toString()

            val bottomSheetView = layoutInflater.inflate(R.layout.info_window, null)
            val markerTitleTextView = bottomSheetView.findViewById<TextView>(R.id.placeName)
            val markerSnippetTextView = bottomSheetView.findViewById<TextView>(R.id.placeCountry)
            val favoriteButton = bottomSheetView.findViewById<Button>(R.id.buttonFavorite)
            val shareButton = bottomSheetView.findViewById<Button>(R.id.buttonShare)

            markerTitleTextView.text = title
            markerSnippetTextView.text = snippet

            val bottomSheetDialog = BottomSheetDialog(requireContext())
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()

            favoriteButton.setOnClickListener {
                try {
                    databaseHelper.newFavoritePlace(
                        title,
                        snippet,
                        marker.position.latitude,
                        marker.position.longitude
                    )
                    bottomSheetDialog.dismiss()
                } catch (e: Exception) {
                    throw Exception("Cannot add this marker")
                }
            }

            shareButton.setOnClickListener {
                val contactFragment = ContactsFragment()

                val bundle = Bundle()
                bundle.putString("title", title)
                bundle.putString("snippet", snippet)
                bundle.putDouble("latitude", marker.position.latitude)
                bundle.putDouble("longitude", marker.position.longitude)
                contactFragment.arguments = bundle

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.map, contactFragment)
                    .addToBackStack(null)
                    .commit()

                bottomSheetDialog.dismiss()
            }

        }
    }

    private fun requestLocationPermission() {
        if ((ContextCompat.checkSelfPermission(
                this.requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
                    == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(
                this.requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
                    == PackageManager.PERMISSION_GRANTED)
        ) {
            locationPermissionGranted = true
            mMap.isMyLocationEnabled = true
            mMap.uiSettings.isMyLocationButtonEnabled = true
            getCurrentLocation()
        } else {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true
                getCurrentLocation()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Ứng dụng cần truy cập vị trí.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getCurrentLocation() {
        if ((ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(
                this.requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
                    != PackageManager.PERMISSION_GRANTED)

        ) {
            requestLocationPermission()
        } else {
            if (locationPermissionGranted) {
                fusedLocationClient.lastLocation.addOnCompleteListener { task ->
                    if (task.isSuccessful && task.result != null) {
                        lastLocation = task.result
                        val currentLatLng = LatLng(lastLocation.latitude, lastLocation.longitude)
                        val geocoder = Geocoder(this.requireContext(), Locale.getDefault())
                        val addresses =
                            geocoder.getFromLocation(
                                currentLatLng.latitude,
                                currentLatLng.longitude,
                                1
                            )
                        if (addresses != null) {
                            mMap.addMarker(
                                MarkerOptions().position(currentLatLng)
                                    .title(addresses[0].getAddressLine(0))
                                    .snippet(addresses[0].countryName)
                            )
                        }
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))

                    }
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    companion object {
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    }

    override fun onMapClick(latLng: LatLng) {
        mMap.clear()
        val geocoder = Geocoder(this.requireContext(), Locale.getDefault())
        val addresses =
            geocoder.getFromLocation(
                latLng.latitude,
                latLng.longitude,
                10
            )
        if (!addresses.isNullOrEmpty()) {
            mMap.addMarker(
                MarkerOptions().position(latLng)
                    .title(addresses[0].getAddressLine(0))
                    .snippet(addresses[0].countryName)
            )
        } else {
            mMap.addMarker(MarkerOptions().position(latLng))
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))
    }
}