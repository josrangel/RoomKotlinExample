package com.jrangel.roomkotlinexample.database

import androidx.room.*
import com.jrangel.roomkotlinexample.entity.User
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface UserDAO {

    @Query("SELECT * FROM user_table ORDER BY id DESC")
    fun getAll(): Maybe<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User): Completable

    @Update
    fun update(user: User): Completable

    @Delete
    fun delete(user: User): Completable

    @Query("DELETE FROM user_table")
    fun deleteAll(): Completable
}