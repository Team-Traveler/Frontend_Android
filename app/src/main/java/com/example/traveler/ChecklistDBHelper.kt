package com.example.traveler

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log

class ChecklistDBHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
    val TAG = "MovieDBHelper"

    companion object {
        const val DB_NAME = "movie_db"
        const val TABLE_NAME = "movie_table"
        const val COL_TITLE = "title"
        const val COL_GENRE = "genre"
        const val COL_ACTOR = "actor"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COL_TITLE TEXT, $COL_GENRE TEXT, $COL_ACTOR TEXT)"
        Log.d(TAG, CREATE_TABLE)
        db?.execSQL(CREATE_TABLE)

        /*샘플 데이터 - 필요할 경우 실행*/
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '명량', '액션', '최민식')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '극한직업', '코미디', '류승룡')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '신과함께', '판타지', '하정우')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '국제시장', '드라마', '황정민')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '어벤져스', '액션', 'Robert Downey Jr.')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '겨울왕국2', '애니메이션', 'Kristen Bell')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVer: Int, newVer: Int) {
        val DROP_TABLE ="DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }
}