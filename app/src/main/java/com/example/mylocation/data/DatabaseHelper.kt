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

        val createSharedPlacesTable = "CREATE TABLE $SHARED_PLACES_TABLE (" +
                "$SHARED_PLACES_TABLE_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$SHARED_PLACES_TABLE_ADDRESS TEXT," +
                "$SHARED_PLACES_TABLE_SNIPPET TEXT," +
                "$SHARED_PLACES_TABLE_LONGITUDE REAL," +
                "$SHARED_PLACES_TABLE_LATITUDE REAL," +
                "$SHARED_PLACES_TABLE_CONTACTS TEXT" +
                ")"

        db?.execSQL(createSharedPlacesTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun newFavoritePlace(
        address: String,
        snippet: String,
        longitude: Double,
        latitude: Double
    ): Long {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(FAVORITE_PLACES_TABLE_ADDRESS, address)
            put(FAVORITE_PLACES_TABLE_SNIPPET, snippet)
            put(FAVORITE_PLACES_TABLE_LONGITUDE, longitude)
            put(FAVORITE_PLACES_TABLE_LATITUDE, latitude)
        }

        return db.insert(FAVORITE_PLACES_TABLE, null, values)
    }

    fun deleteFavoritePlace(id: Long): Int {
        val db = this.writableDatabase

        val selectedFavoritePlace = "$id LIKE ?"

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
                val id = it.getLong(it.getColumnIndexOrThrow(FAVORITE_PLACES_TABLE_ID))
                val address = it.getString(it.getColumnIndexOrThrow(FAVORITE_PLACES_TABLE_ADDRESS))
                val snippet = it.getString(it.getColumnIndexOrThrow(FAVORITE_PLACES_TABLE_SNIPPET))
                val longitude = it.getDouble(
                    it.getColumnIndexOrThrow(
                        FAVORITE_PLACES_TABLE_LONGITUDE
                    )
                )
                val latitude =
                    it.getDouble(it.getColumnIndexOrThrow(FAVORITE_PLACES_TABLE_LATITUDE))

                val favoritePlace = FavoritePlace(id, address, snippet, longitude, latitude)
                favoritePlaces.add(favoritePlace)
            }
        }
        cursor.close()

        return favoritePlaces
    }

    fun newSharedPlace(
        address: String,
        snippet: String,
        longitude: Double,
        latitude: Double,
        contacts: List<String>
    ): Long {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(SHARED_PLACES_TABLE_ADDRESS, address)
            put(SHARED_PLACES_TABLE_SNIPPET, snippet)
            put(SHARED_PLACES_TABLE_LONGITUDE, longitude)
            put(SHARED_PLACES_TABLE_LATITUDE, latitude)
            put(SHARED_PLACES_TABLE_CONTACTS, contacts.joinToString(","))
        }

        return db.insert(SHARED_PLACES_TABLE, null, values)
    }

    fun getSharedPlaces(): MutableList<SharedPlace> {
        val db = this.readableDatabase

        val sharedPlaces = mutableListOf<SharedPlace>()
        val selectQuery = "SELECT * FROM $SHARED_PLACES_TABLE"

        val cursor = db.rawQuery(selectQuery, null)

        cursor.use {
            while (it.moveToNext()) {
                val id = it.getLong(it.getColumnIndexOrThrow(SHARED_PLACES_TABLE_ID))
                val address = it.getString(it.getColumnIndexOrThrow(SHARED_PLACES_TABLE_ADDRESS))
                val snippet = it.getString(it.getColumnIndexOrThrow(SHARED_PLACES_TABLE_SNIPPET))
                val longitude = it.getDouble(it.getColumnIndexOrThrow(SHARED_PLACES_TABLE_LONGITUDE))
                val latitude = it.getDouble(it.getColumnIndexOrThrow(SHARED_PLACES_TABLE_LATITUDE))
                val contacts = it.getString(it.getColumnIndexOrThrow(SHARED_PLACES_TABLE_CONTACTS)).split(",")

                val sharedPlace = SharedPlace(id, address, snippet, longitude, latitude, contacts)
                sharedPlaces.add(sharedPlace)
            }
        }

        return sharedPlaces
    }


    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "MyLocationSqlite.db"

        const val FAVORITE_PLACES_TABLE = "favorite_places"
        const val FAVORITE_PLACES_TABLE_ID = "id"
        const val FAVORITE_PLACES_TABLE_ADDRESS = "address"
        const val FAVORITE_PLACES_TABLE_SNIPPET = "snippet"
        const val FAVORITE_PLACES_TABLE_LONGITUDE = "longitude"
        const val FAVORITE_PLACES_TABLE_LATITUDE = "latitude"

        const val SHARED_PLACES_TABLE = "shared_places"
        const val SHARED_PLACES_TABLE_ID = "id"
        const val SHARED_PLACES_TABLE_ADDRESS = "address"
        const val SHARED_PLACES_TABLE_SNIPPET = "snippet"
        const val SHARED_PLACES_TABLE_LONGITUDE = "longitude"
        const val SHARED_PLACES_TABLE_LATITUDE = "latitude"
        const val SHARED_PLACES_TABLE_CONTACTS = "contacts"

    }
}