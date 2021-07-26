package com.jrangel.roomkotlinexample

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jrangel.roomkotlinexample.adapter.UserAdapter
import com.jrangel.roomkotlinexample.database.DatabaseRoom
import com.jrangel.roomkotlinexample.database.UserRepository
import com.jrangel.roomkotlinexample.entity.User
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

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
    lateinit var rvUser: RecyclerView
    lateinit var userAdapter: UserAdapter
    lateinit var dataRV: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = UserRepository().dbInit(baseContext)

        etFirstName = findViewById(R.id.et_first_name)
        etLastName = findViewById(R.id.et_last_name)
        etAge = findViewById(R.id.et_age)
        rvUser = findViewById(R.id.rv_user)

        chargeUsers()
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this)
        rvUser.layoutManager = linearLayoutManager
        userAdapter = UserAdapter(dataRV)
        // Setting the Adapter with the recyclerview
        rvUser.adapter = userAdapter

    }

    private fun chargeUsers() {
        doAsync {
            dataRV = db.userDao().getAll() as ArrayList<User>
            uiThread {
                initRecyclerView()
            }
        }
    }

    private fun notifyNewUser() {
        userAdapter.notifyItemInserted(0)
        rvUser.scrollToPosition(0)
    }

    fun addNewUser(view: View) {
        val user = User()
        user.firstName = etFirstName.text.toString()
        user.lastName = etLastName.text.toString()
        user.age = etAge.text.toString().toInt()
        doAsync {
            db.userDao().insert(user)
            dataRV.add(0, user)
            uiThread {
                notifyNewUser()
            }
        }
    }
}