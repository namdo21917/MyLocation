package com.example.mylocation.adapter

import com.example.mylocation.data.FavoritePlace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mylocation.R

class FavoritePlaceAdapter(private val favoritePlaces: MutableList<FavoritePlace>) :
    RecyclerView.Adapter<FavoritePlaceAdapter.FavoritePlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritePlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_place, parent, false)

        return FavoritePlaceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return favoritePlaces.size
    }

    override fun onBindViewHolder(holder: FavoritePlaceViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    inner class FavoritePlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}