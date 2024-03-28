package com.ilyaselmabrouki.db_sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class Personne(val id: Int, val name: String)

class DataBaseHelper(context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE IF NOT EXISTS $TABLE_PERSONNE (
                $COLUMN_ID INTEGER PRIMARY KEY,
                $COLUMN_NAME TEXT
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrades here
        // You may need to alter table structures or perform other migrations
    }

    fun addPersonne(name: String): Long {
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
        }

        val db = writableDatabase
        return db.insert(TABLE_PERSONNE, null, values)
    }

    @SuppressLint("Range")
    fun getPersonnes(): List<Personne> {
        val personnes = mutableListOf<Personne>()
        val db = readableDatabase
        val cursor: Cursor? = db.rawQuery("SELECT * FROM $TABLE_PERSONNE", null)
        cursor?.use {
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                personnes.add(Personne(id, name))
            }
        }
        return personnes
    }

    fun updatePersonne(personne: Personne): Int {
        val values = ContentValues().apply {
            put(COLUMN_NAME, personne.name)
        }

        val db = writableDatabase
        return db.update(
            TABLE_PERSONNE,
            values,
            "$COLUMN_ID = ?",
            arrayOf(personne.id.toString())
        )
    }

    fun deletePersonne(id: Int): Int {
        val db = writableDatabase
        return db.delete(
            TABLE_PERSONNE,
            "$COLUMN_ID = ?",
            arrayOf(id.toString())
        )
    }
    companion object {
        // If you change the database schema, you must increment the database version.
        private const val DATABASE_NAME = "my_database.db"
        private const val DATABASE_VERSION = 1

        // Table name and columns
        const val TABLE_PERSONNE = "personne"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
    }
}