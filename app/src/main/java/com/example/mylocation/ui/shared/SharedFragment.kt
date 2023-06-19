package com.example.mylocation.ui.shared

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mylocation.R
import com.example.mylocation.adapter.FavoritePlaceAdapter
import com.example.mylocation.adapter.SharedPlaceAdapter
import com.example.mylocation.data.DatabaseHelper
import com.example.mylocation.databinding.FragmentSharedBinding

class SharedFragment : Fragment() {

    private var _binding: FragmentSharedBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPlaceAdapter: SharedPlaceAdapter
    private lateinit var sharedPlaceRecyclerView: RecyclerView
    private lateinit var databaseHelper : DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSharedBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPlaceRecyclerView = view.findViewById(R.id.recycler_view_shared_places)
        sharedPlaceRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val emptyImage: ImageView = view.findViewById(R.id.image_empty_shared_place)

        databaseHelper = DatabaseHelper(requireContext())
        val sharedPlaceList = databaseHelper.getSharedPlaces()
        if (sharedPlaceList.isEmpty()) {
            sharedPlaceRecyclerView.visibility = View.GONE
            emptyImage.visibility = View.VISIBLE
        } else {
            sharedPlaceRecyclerView.visibility = View.VISIBLE
            emptyImage.visibility = View.GONE

            sharedPlaceAdapter = SharedPlaceAdapter(sharedPlaceList)
            sharedPlaceRecyclerView.adapter = sharedPlaceAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        updateSharedList()
    }

    private fun updateSharedList() {
        val sharedPlaceList = databaseHelper.getSharedPlaces()

        sharedPlaceAdapter = SharedPlaceAdapter(sharedPlaceList)
        sharedPlaceRecyclerView.adapter = sharedPlaceAdapter

    }

}