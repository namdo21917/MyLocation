package com.example.mylocation.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mylocation.R
import com.example.mylocation.adapter.FavoritePlaceAdapter
import com.example.mylocation.data.DatabaseHelper
import com.example.mylocation.data.FavoritePlace
import com.example.mylocation.databinding.FragmentFavoritesBinding


class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoritePlaceRecyclerView: RecyclerView
    private lateinit var adapter: FavoritePlaceAdapter
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoritePlaceRecyclerView = view.findViewById(R.id.recycler_view_favorite_places)
        val emptyImage: ImageView = view.findViewById(R.id.image_empty_favorite_place)
        favoritePlaceRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        databaseHelper = DatabaseHelper(requireContext())
        val favoritePlaceList = databaseHelper.getFavoritePlaces()
        if (favoritePlaceList.isEmpty()) {
            favoritePlaceRecyclerView.visibility = View.GONE
            emptyImage.visibility = View.VISIBLE
        } else {
            favoritePlaceRecyclerView.visibility = View.VISIBLE
            emptyImage.visibility = View.GONE

            adapter = FavoritePlaceAdapter(favoritePlaceList)
            adapter.onDeleteClick = { favoritePlace ->
                databaseHelper.deleteFavoritePlace(favoritePlace.id)
                updateFavoriteList()
            }
            favoritePlaceRecyclerView.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        updateFavoriteList()
    }

    private fun updateFavoriteList() {
        val favoritePlaceList = databaseHelper.getFavoritePlaces()

        adapter = FavoritePlaceAdapter(favoritePlaceList)
        favoritePlaceRecyclerView.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}