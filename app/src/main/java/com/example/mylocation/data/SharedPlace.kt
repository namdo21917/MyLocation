package com.example.mylocation.data

data class SharedPlace(
        val id: Long,
        val address: String,
        val snippet: String,
        val latitude: Double,
        val longitude: Double,
        val contacts: List<String>
)
