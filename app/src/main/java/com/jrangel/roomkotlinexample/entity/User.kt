package com.jrangel.roomkotlinexample.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
class User {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    @ColumnInfo(name = "first_name")
    var firstName: String = ""
    @ColumnInfo(name = "last_name")
    var lastName: String = ""
    @ColumnInfo(name = "age")
    var age: Int = 0
}