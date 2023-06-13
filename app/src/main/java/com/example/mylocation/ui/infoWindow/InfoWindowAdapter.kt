package com.example.mylocation.ui.infoWindow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.mylocation.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class InfoWindowAdapter(private val context: Context) : GoogleMap.InfoWindowAdapter {
    override fun getInfoWindow(marker: Marker): View? {
        return null
    }

    override fun getInfoContents(marker: Marker): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.info_window, null)

        val titleTextView = view.findViewById<TextView>(R.id.title)
        val snippetTextView = view.findViewById<TextView>(R.id.snippet)
        val favoriteButton = view.findViewById<ImageButton>(R.id.favorite_button)
        val shareButton = view.findViewById<ImageButton>(R.id.share_button)

        titleTextView.text = marker.title
        snippetTextView.text = marker.snippet

        favoriteButton.setOnClickListener {
            Toast.makeText(view.context, "Test yêu thích", Toast.LENGTH_SHORT)
        }

        shareButton.setOnClickListener {
            Toast.makeText(view.context, "Test chia sẻ", Toast.LENGTH_SHORT)
        }

        return view
    }
}