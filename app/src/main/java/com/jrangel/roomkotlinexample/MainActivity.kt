package com.jrangel.roomkotlinexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jrangel.roomkotlinexample.database.UserRepository
import com.jrangel.roomkotlinexample.entity.User
import org.jetbrains.anko.doAsync

/**
 * Tutoriales
 *
 * Room
 * https://developer.android.com/codelabs/android-room-with-a-view-kotlin?hl=es-419
 *
 * Anko
 * https://github.com/Kotlin/anko
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = UserRepository().dbInit(baseContext)
        val user = User()
        user.firstName = "memin"
        user.lastName = "canallin"
        user.age = 25
        doAsync {
            db.userDao().insert(user)
        }

    }
}