package yogispark.yoginote.Helpers

/**
 * Created by yogesh on 16/7/17.
 */

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DbHelper(context: Context) : SQLiteOpenHelper(context, "example.db", null, 4) {
    val TAG = "DATABASE"
    val TABLE = "tasks"

    companion object {
        public val ID: String = "_id"
        public val TIMESTAMP: String = "TIMESTAMP"
        public val TASK: String = "TEXT"
        public val EXPIRY: String = "EXPIRY"
        public val FINISHED: String = "FINISHED"
        public val REPEAT: String = "REPEAT"
        public val ALARMTONE: String = "ALARMTONE"
    }

    val DATABASE_CREATE =
            "CREATE TABLE if not exists " + TABLE + " (" +
                    "${ID} integer PRIMARY KEY autoincrement," +
                    "${TIMESTAMP} integer," +
                    "${TASK} text,"+
                    "${EXPIRY} integer,"+
                    "${FINISHED} text,"+
                    "${REPEAT} integer,"+
                    "${ALARMTONE} text"+
                    ")"

    fun addTask(text: String, expiry: Long, finished: String, repeat: Long, alarmTone: String) {
        val values = ContentValues()
        values.put(TASK, text)
        values.put(TIMESTAMP, System.currentTimeMillis())
        values.put(EXPIRY, expiry)
        values.put(FINISHED, finished)
        values.put(REPEAT, repeat)
        values.put(ALARMTONE, alarmTone)
        getWritableDatabase().insert(TABLE, null, values);
    }

    fun getTasks() : Cursor {
        return getReadableDatabase()
                .query(TABLE, arrayOf(ID, TIMESTAMP, TASK, EXPIRY, FINISHED, REPEAT, ALARMTONE), null, null, null, null, null);
    }

    override fun onCreate(db: SQLiteDatabase) {
        Log.d(TAG, "Creating: " + DATABASE_CREATE);
        db.execSQL(DATABASE_CREATE)
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {
    }

}