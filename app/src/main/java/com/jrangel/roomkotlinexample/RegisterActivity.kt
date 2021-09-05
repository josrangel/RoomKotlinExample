package com.jrangel.roomkotlinexample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class RegisterActivity : AppCompatActivity() {

    lateinit var etFirstName: EditText
    lateinit var etLastName: EditText
    lateinit var etAge: EditText
    var id = 0
    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etFirstName = findViewById(R.id.et_first_name)
        etLastName = findViewById(R.id.et_last_name)
        etAge = findViewById(R.id.et_age)

        recoverData()
    }

    private fun recoverData() {
        val extras = intent.extras
        etFirstName.setText(extras?.getString(MainActivity.EXTRA_FIRST_NAME))
        etLastName.setText(extras?.getString(MainActivity.EXTRA_LAST_NAME))
        etAge.setText(extras?.getInt(MainActivity.EXTRA_AGE).toString())
        id = extras?.getInt(MainActivity.EXTRA_ID)!!
        position = extras?.getInt(MainActivity.EXTRA_POSITION)!!
    }

    fun saveChanges(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.EXTRA_ID, id)
        intent.putExtra(MainActivity.EXTRA_FIRST_NAME, etFirstName.text.toString())
        intent.putExtra(MainActivity.EXTRA_LAST_NAME, etLastName.text.toString())
        intent.putExtra(MainActivity.EXTRA_AGE, etAge.text.toString().toInt())
        intent.putExtra(MainActivity.EXTRA_POSITION, position)
        setResult(RESULT_OK, intent)
        finish()
    }
}