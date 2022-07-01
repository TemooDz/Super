package com.university.superheroes.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "CHARACTERS_DB", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS CHARACTERS(id NUMBER PRIMARY KEY, name TEXT, image TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            db?.execSQL("DROP TABLE IF EXISTS CHARACTERS");
            onCreate(db);
        }
    }

    fun addCharacter(characterName: String, characterImage: String) {
        val db = writableDatabase
        db.beginTransaction()
        try {
            val values = ContentValues()
            values.put("name", characterName)
            values.put("image", characterImage)
            db.insertOrThrow("CHARACTERS", null, values)
            db.setTransactionSuccessful()
        } catch (e: Exception) {
            db.endTransaction()
        } finally {
            db.endTransaction()
        }
    }

    fun getById(id: Int): Cursor? {
        return readableDatabase.rawQuery("SELECT * FROM CHARACTERS WHERE id = $id", null)
    }

    fun deleteTable() {
        writableDatabase.execSQL("DELETE FROM city_name")
    }
}
