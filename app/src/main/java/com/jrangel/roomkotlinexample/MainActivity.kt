package com.jrangel.roomkotlinexample

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.jrangel.roomkotlinexample.database.DatabaseRoom
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

    lateinit var etFirstName: EditText
    lateinit var etLastName: EditText
    lateinit var etAge: EditText
    lateinit var db: DatabaseRoom

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = UserRepository().dbInit(baseContext)

        etFirstName = findViewById(R.id.et_first_name)
        etLastName = findViewById(R.id.et_last_name)
        etAge = findViewById(R.id.et_age)
    }

    fun addNewUser(view: View) {
        val user = User()
        user.firstName = etFirstName.text.toString()
        user.lastName = etLastName.text.toString()
        user.age = etAge.text.toString().toInt()
        doAsync {
            db.userDao().insert(user)
        }
    }
}