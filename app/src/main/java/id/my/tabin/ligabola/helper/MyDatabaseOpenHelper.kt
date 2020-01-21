package id.my.tabin.ligabola.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import id.my.tabin.ligabola.model.FavouriteEvent
import id.my.tabin.ligabola.model.FavouriteTeam
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) :
    ManagedSQLiteOpenHelper(ctx, "FavouriteEvent.db", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(
            FavouriteEvent.TABLE_FAVOURITE_EVENT, true,
            FavouriteEvent.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavouriteEvent.ID_EVENT to TEXT + UNIQUE
        )
        db.createTable(
            FavouriteTeam.TABLE_FAVOURITE_TEAM, true,
            FavouriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavouriteTeam.ID_TEAM to TEXT + UNIQUE
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(FavouriteEvent.TABLE_FAVOURITE_EVENT, true)
        db.dropTable(FavouriteTeam.TABLE_FAVOURITE_TEAM, true)
    }
}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)