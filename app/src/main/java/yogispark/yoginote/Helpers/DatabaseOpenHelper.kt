package yogispark.yoginote.Helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

/**
 * Created by yogesh on 16/7/17.
 */
class DatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "MyDatabase", null, 1) {
    val TABLE = "tasks"

    companion object {
        private var instance: DatabaseOpenHelper? = null
        public val ID: String = "_id"
        public val TIMESTAMP: String = "TIMESTAMP"
        public val TASK: String = "TASK"
        public val EXPIRY: String = "EXPIRY"
        public val FINISHED: String = "FINISHED"
        public val REPEAT: String = "REPEAT"
        public val ALARMTONE: String = "ALARMTONE"


        @Synchronized
        fun getInstance(ctx: Context): DatabaseOpenHelper {
            if (instance == null) {
                instance = DatabaseOpenHelper(ctx.getApplicationContext())
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db?.createTable(TABLE, true,
                "${ID}" to INTEGER + PRIMARY_KEY,
                "${TASK}" to TEXT,
                "${TIMESTAMP}" to INTEGER,
                "${EXPIRY}" to INTEGER,
                "${FINISHED}" to INTEGER,
                "${REPEAT}" to INTEGER,
                "${ALARMTONE}" to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db?.dropTable(TABLE, true)
        onCreate(db)
    }
}

// Access property for Context
val Context.database: DatabaseOpenHelper
    get() = DatabaseOpenHelper.getInstance(getApplicationContext())