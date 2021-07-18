package com.jrangel.roomkotlinexample.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jrangel.roomkotlinexample.entity.User

@Database(entities = arrayOf(User::class), version = 1)
abstract class DatabaseRoom : RoomDatabase() {
    abstract fun userDao(): UserDAO

    //singleton
    companion object {
        @Volatile
        private var INSTANCE: DatabaseRoom? = null

        fun getDatabase(context: Context): DatabaseRoom {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    DatabaseRoom::class.java,
                    "DatabaseUserApp"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}