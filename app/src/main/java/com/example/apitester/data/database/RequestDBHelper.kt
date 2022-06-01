package com.example.apitester.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class RequestDBHelper(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "re_db.db"

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${RequestContract.RequestEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${RequestContract.RequestEntry.COLUMN_NAME_URL} TEXT," +
                    "${RequestContract.RequestEntry.COLUMN_NAME_REQUEST_TYPE} INTEGER," +
                    "${RequestContract.RequestEntry.COLUMN_NAME_HEADERS} TEXT," +
                    "${RequestContract.RequestEntry.COLUMN_NAME_QUERIES} TEXT," +
                    "${RequestContract.RequestEntry.COLUMN_NAME_REQUEST_BODY} TEXT," +
                    "${RequestContract.RequestEntry.COLUMN_NAME_STATUS} INTEGER," +
                    "${RequestContract.RequestEntry.COLUMN_NAME_CODE} INTEGER," +
                    "${RequestContract.RequestEntry.COLUMN_NAME_RESPONSE_BODY} TEXT," +
                    "${RequestContract.RequestEntry.COLUMN_NAME_TIMESTAMP} DATETIME DEFAULT TIMESTAMP)"


        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${RequestContract.RequestEntry.TABLE_NAME}"
    }
}