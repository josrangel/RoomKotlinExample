package com.jrangel.roomkotlinexample.database

import android.content.Context

class UserRepository {
    fun dbInit(context: Context): DatabaseRoom {
        return DatabaseRoom.getDatabase(context)
    }
}