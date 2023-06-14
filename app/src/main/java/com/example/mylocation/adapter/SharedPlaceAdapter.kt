package com.example.mylocation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mylocation.R
import com.example.mylocation.data.SharedPlace

class SharedPlaceAdapter(private val sharedPlaces: MutableList<SharedPlace>) :
    RecyclerView.Adapter<SharedPlaceAdapter.SharedPlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SharedPlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shared_place, parent, false)

        return SharedPlaceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return sharedPlaces.size
    }

    override fun onBindViewHolder(holder: SharedPlaceViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    inner class SharedPlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}