package com.example.mylocation.ui.favorites

import FavoritePlace
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class SharedPreferencesHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("FavoritePlaces", Context.MODE_PRIVATE)
    private val gson: Gson = Gson()

    fun saveFavoritePlace(place: FavoritePlace) {
        val editor = sharedPreferences.edit()
        val json = gson.toJson(place)
        editor.putString(place.id.toString(), json)
        editor.apply()
    }

    fun getFavoritePlaces(): List<FavoritePlace> {
        val places = mutableListOf<FavoritePlace>()
        val keys = sharedPreferences.all.keys
        for (key in keys) {
            val json = sharedPreferences.getString(key, null)
            json?.let {
                val place = gson.fromJson(it, FavoritePlace::class.java)
                place?.let {
                    places.add(place)
                }
            }
        }
        return places
    }
}
