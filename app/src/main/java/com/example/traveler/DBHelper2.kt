package com.example.traveler

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper2(context: Context?) : SQLiteOpenHelper(context, DB_MAKE_PLAN, null, 1) {
    val TAG = "MakePlanDBHelper"

    companion object{
        const val DB_MAKE_PLAN = "make_plan_db"
        const val COLUMN_ID = "id"
        const val TABLE_NAME = "plan_table"
        const val COL_PLAN_DESTINATION = "destination"
        const val COL_PLAN_DAYS = "days"
        const val COL_PLAN_WHO = "who"
        const val COL_PLAN_WHAT = "what"
        const val COL_PLAN_INTENSITY = "intensity"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createplandb = """
        CREATE TABLE $TABLE_NAME(
        ${DBHelper.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
        $COL_PLAN_DESTINATION TEXT,
        $COL_PLAN_DAYS TEXT,
        $COL_PLAN_WHO TEXT,
        $COL_PLAN_WHAT TEXT,
        $COL_PLAN_INTENSITY TEXT
        )
    """

        Log.d(TAG, createplandb)
        db?.execSQL(createplandb)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVer: Int, newVer: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS ${TABLE_NAME}"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }
}