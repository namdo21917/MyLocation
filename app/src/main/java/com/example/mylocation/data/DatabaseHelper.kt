package com.example.mylocation.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream


class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val databaseFile: File = context.getDatabasePath(DATABASE_NAME)
    private val databasePath: String = context.getDatabasePath(DATABASE_NAME).absolutePath
    private lateinit var mDatabase: SQLiteDatabase
    private val mContext = context

    override fun onCreate(db: SQLiteDatabase?) {
        onCreateDatabase()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun checkDatabase(): Boolean {
        return databaseFile.exists()
    }

    fun copyDatabase() {
        val mInput: InputStream = mContext.assets.open(DATABASE_NAME)
        val mOutput: OutputStream = FileOutputStream(databaseFile)
        val mBuffer = ByteArray(1024)
        var mLength: Int
        while (mInput.read(mBuffer).also { mLength = it } > 0) {
            mOutput.write(mBuffer, 0, mLength)
        }
        mOutput.flush()
        mOutput.close()
        mInput.close()
    }

    fun onCreateDatabase() {
        val isDatabaseExist: Boolean = checkDatabase()
        if (!isDatabaseExist) {
            readableDatabase
            close()
            try {
                copyDatabase()
            } catch (e: Exception) {
                throw Exception("Cannot create database!")
            }
        }
    }

    fun openDatabase() {
        mDatabase = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READONLY);
    }

    fun addFavoriteList(address: String, snippet: String, longitude: Double, latitude: Double) {
        val db = this.writableDatabase
        this.openDatabase()
        val values = ContentValues().apply {
            put("address", address)
            put("snippet", snippet)
            put("longitude", longitude)
            put("latitude", latitude)
        }
      //  val query =  "INSERT INTO favorite_places (address, snippet, longitude, latitude) VALUES (${"$address"}, ${"$snippet"}, ${longitude}, ${latitude})"
        try {
//            db.rawQuery(query, null)
            db.insert("favorite_places", null, values)
        } catch (e: Exception) {
            throw Exception("Cannot add favorite place to database!")
        }
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "MyLocationSqlite.db"
        const val FAVORITE_PLACES_TABLE = "favorite_places"
        const val SHARED_PLACES_TABLE = "shared_places"

    }
}