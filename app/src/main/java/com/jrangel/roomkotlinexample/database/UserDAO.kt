package com.jrangel.roomkotlinexample.database

import androidx.room.*
import com.jrangel.roomkotlinexample.entity.User

@Dao
interface UserDAO {

    @Query("SELECT * FROM user_table")
    fun getAlphabetizedWords(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM user_table")
    fun deleteAll()
}