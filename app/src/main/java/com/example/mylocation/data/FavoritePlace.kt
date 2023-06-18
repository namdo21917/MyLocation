package com.example.mylocation.data


data class FavoritePlace(
        val id: Long,
        val address: String,
        val snippet: String,
        val latitude: Double,
        val longitude: Double
)
