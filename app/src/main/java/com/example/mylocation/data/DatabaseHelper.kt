package com.example.mylocation.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createFavoritePlacesTable = "CREATE TABLE $FAVORITE_PLACES_TABLE (" +
                "$FAVORITE_PLACES_TABLE_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$FAVORITE_PLACES_TABLE_ADDRESS TEXT," +
                "$FAVORITE_PLACES_TABLE_SNIPPET TEXT," +
                "$FAVORITE_PLACES_TABLE_LONGITUDE REAL," +
                "$FAVORITE_PLACES_TABLE_LATITUDE REAL" +
                ")"
        db?.execSQL(createFavoritePlacesTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun newFavoritePlace(address: String, snippet: String, longitude: Double, latitude: Double): Long {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put("address", address)
            put("snippet", snippet)
            put("longitude", longitude)
            put("latitude", latitude)
        }

        return db.insert(FAVORITE_PLACES_TABLE, null, values)
    }

    fun deleteFavoritePlace(id: Long): Int{
        val db = this.writableDatabase

        val selectedFavoritePlace= "$id LIKE ?"

        val selectionArgs = arrayOf(id.toString())

        return db.delete(FAVORITE_PLACES_TABLE, selectedFavoritePlace, selectionArgs)
    }

    fun getFavoritePlaces(): MutableList<FavoritePlace> {
        val db = this.readableDatabase

        val favoritePlaces = mutableListOf<FavoritePlace>()
        val selectQuery = "SELECT * FROM $FAVORITE_PLACES_TABLE"
        val cursor = db.rawQuery(selectQuery, null)

        cursor.use {
            while (it.moveToNext()) {
                val id = it.getLong(it.getColumnIndexOrThrow("id"))
                val address = it.getString(it.getColumnIndexOrThrow("address"))
                val snippet = it.getString(it.getColumnIndexOrThrow("snippet"))
                val longitude = it.getDouble(it.getColumnIndexOrThrow("longitude"))
                val latitude = it.getDouble(it.getColumnIndexOrThrow("latitude"))

                val favoritePlace = FavoritePlace(id, address, snippet, longitude, latitude)
                favoritePlaces.add(favoritePlace)
            }
        }
        cursor.close()

        return favoritePlaces
    }


    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "MyLocationSqlite.db"

        const val FAVORITE_PLACES_TABLE = "favorite_places"
        const val FAVORITE_PLACES_TABLE_ID = "id"
        const val FAVORITE_PLACES_TABLE_ADDRESS= "address"
        const val FAVORITE_PLACES_TABLE_SNIPPET = "snippet"
        const val FAVORITE_PLACES_TABLE_LONGITUDE = "longitude"
        const val FAVORITE_PLACES_TABLE_LATITUDE = "latitude"

        const val SHARED_PLACES_TABLE = "shared_places"
        const val SHARED_PLACES_TABLE_ID = "id"
        const val SHARED_PLACES_TABLE_ADDRESS= "address"
        const val SHARED_PLACES_TABLE_SNIPPET = "snippet"
        const val SHARED_PLACES_TABLE_LONGITUDE = "longitude"
        const val SHARED_PLACES_TABLE_LATITUDE = "latitude"


    }
}