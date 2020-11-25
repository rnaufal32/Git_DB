package com.zulham.githubusersearch.Database.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import com.zulham.githubusersearch.Database.db.DatabaseContract.FavColumns.Companion.TABLE_NAME
import com.zulham.githubusersearch.Database.db.DatabaseContract.FavColumns.Companion._ID
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import kotlin.jvm.Throws

class FavHelper(context: Context) {

    companion object{
        private const val DB_TABLE = TABLE_NAME
        private lateinit var databaseHelper: DatabaseHelper
        private var INSTANCE: FavHelper? = null
        @InternalCoroutinesApi
        fun getInstance(context: Context): FavHelper =
                INSTANCE ?: synchronized(this){
                    INSTANCE ?: FavHelper(context)
                }

        private lateinit var database: SQLiteDatabase
    }

    init {
        databaseHelper = DatabaseHelper(context)
    }

    @Throws(SQLiteException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor{
        return database.query(
                DB_TABLE,
                null,
                null,
                null,
                null,
                null,
                null,
                "$_ID ASC"
        )
    }

    fun queryById(id: String): Cursor {
        return database.query(
                DB_TABLE,
                null,
                "$_ID = ?",
                arrayOf(id),
                null,
                null,
                null,
                null)
    }

    fun insert(value: ContentValues?): Long{
        return database.insert(DB_TABLE, null, value)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(DB_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    fun deleteById(id: String): Int{
        return database.delete(DB_TABLE, "$_ID = '$id", null)
    }


}