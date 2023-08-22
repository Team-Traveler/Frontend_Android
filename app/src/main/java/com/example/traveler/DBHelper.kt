package com.example.traveler

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        //db내용
        private const val DATABASE_VERSION = 20
        private const val DATABASE_NAME = "TravelReviews.db"

        const val TABLE_TRIP_NAMES = "trip_names"
        const val TABLE_NAME = "reviews"
        const val COLUMN_ID = "id"
        const val COLUMN_TRIPNAME = "tripName"
        const val COLUMN_TRIPDAYNIGHT = "tripDayNight"
        const val COLUMN_TRIPDATE = "tripDate"
        const val COLUMN_HASHTAG = "hashtag"
        const val COLUMN_GOOD = "good"
        const val COLUMN_SHAME = "shame"
        const val COLUMN_ONELINE = "oneline"
        const val COLUMN_RTBSCORE = "rtbScore"
        const val COLUMN_RTBFOOD = "rtbFood"
        const val COLUMN_RTBACTIVITY = "rtbActivity"
        const val COLUMN_SELECTEDMEMBER = "selectedMember"
        const val COLUMN_SELECTEDHOWMANY = "selectedHowmany"
        const val COLUMN_SELECTEDINTENSITY = "selectedIntensity"
        const val COLUMN_SELECTEDTHEME = "selectedTheme"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
        CREATE TABLE $TABLE_TRIP_NAMES (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_TRIPNAME TEXT,
            $COLUMN_HASHTAG TEXT,
            $COLUMN_TRIPDAYNIGHT TEXT,
            $COLUMN_TRIPDATE TEXT,
            $COLUMN_GOOD TEXT,
            $COLUMN_SHAME TEXT,
            $COLUMN_ONELINE TEXT,
            $COLUMN_RTBSCORE REAL,
            $COLUMN_RTBFOOD REAL,
            $COLUMN_RTBACTIVITY REAL,
            $COLUMN_SELECTEDMEMBER TEXT,
            $COLUMN_SELECTEDHOWMANY TEXT,
            $COLUMN_SELECTEDINTENSITY TEXT,
            $COLUMN_SELECTEDTHEME TEXT
        )
    """

        //Log.d("DB내용", createTableQuery)
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TRIP_NAMES")
        onCreate(db)
    }

}