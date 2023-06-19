package com.example.mylocation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mylocation.R
import com.example.mylocation.data.Contact
import com.example.mylocation.data.FavoritePlace

class FavoritePlaceAdapter(private val favoritePlaces: MutableList<FavoritePlace>) :
    RecyclerView.Adapter<FavoritePlaceAdapter.FavoritePlaceViewHolder>() {

    var onDeleteClick: ((FavoritePlace) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritePlaceViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.favorite_place, parent, false)

        return FavoritePlaceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return favoritePlaces.size
    }

    override fun onBindViewHolder(holder: FavoritePlaceViewHolder, position: Int) {
        val place = favoritePlaces[position]
        holder.bind(place)
    }

    inner class FavoritePlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val placeName: TextView = itemView.findViewById(R.id.text_favorite_place_name)
        private val placeSnippet: TextView = itemView.findViewById(R.id.text_favorite_place_snippet)
        private val deleteButton: Button = itemView.findViewById(R.id.delete_favorite)

        fun bind(favoritePlace: FavoritePlace) {
            placeName.text = favoritePlace.address
            placeSnippet.text = favoritePlace.snippet
        }

        init {
            deleteButton.setOnClickListener {
                onDeleteClick?.invoke(favoritePlaces[adapterPosition])
            }

        }
    }
}