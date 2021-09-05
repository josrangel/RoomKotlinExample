package com.jrangel.roomkotlinexample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jrangel.roomkotlinexample.adapter.UserAdapter
import com.jrangel.roomkotlinexample.database.DatabaseRoom
import com.jrangel.roomkotlinexample.database.UserRepository
import com.jrangel.roomkotlinexample.entity.User
import com.jrangel.roomkotlinexample.listeners.OnDeleteListener
import com.jrangel.roomkotlinexample.listeners.OnSelectListener
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

class MainActivity : AppCompatActivity(), OnDeleteListener, OnSelectListener {

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etAge: EditText
    private lateinit var db: DatabaseRoom
    private lateinit var rvUser: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private lateinit var dataRV: ArrayList<User>
    private lateinit var previewRequest: ActivityResultLauncher<Intent>

    companion object {
        const val EXTRA_ID = "id"
        const val EXTRA_FIRST_NAME = "firstName"
        const val EXTRA_LAST_NAME = "lastName"
        const val EXTRA_AGE = "age"
        const val EXTRA_POSITION = "position"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = UserRepository().dbInit(baseContext)

        etFirstName = findViewById(R.id.et_first_name)
        etLastName = findViewById(R.id.et_last_name)
        etAge = findViewById(R.id.et_age)
        rvUser = findViewById(R.id.rv_user)

        chargeUsers()

        previewRequest =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    val extras = it.data?.extras
                    val user = User()
                    user.id = extras?.getInt(EXTRA_ID)!!
                    user.firstName = extras?.getString(EXTRA_FIRST_NAME)!!
                    user.lastName = extras?.getString(EXTRA_LAST_NAME)!!
                    user.age = extras?.getInt(EXTRA_AGE)
                    val position = extras?.getInt(EXTRA_POSITION)
                    updateUser(user, position)
                }
            }
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this)
        rvUser.layoutManager = linearLayoutManager
        userAdapter = UserAdapter(dataRV, this)
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
        userAdapter.notifyItemRangeChanged(0, userAdapter.itemCount)
        rvUser.scrollToPosition(0)
    }

    private fun notifyDeleteUser(position: Int) {
        userAdapter.notifyItemRemoved(position)
        userAdapter.notifyItemRangeChanged(position, userAdapter.itemCount)
    }

    private fun notifyChangeUser(position: Int) {
        userAdapter.notifyItemChanged(position)
        rvUser.scrollToPosition(position)
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

    override fun deleteElement(user: User, position: Int) {
        doAsync {
            db.userDao().delete(user)
            dataRV.remove(user)
            uiThread {
                notifyDeleteUser(position)
            }
        }
    }

    private fun updateUser(user: User, position: Int) {
        doAsync {
            db.userDao().update(user)
            dataRV[position] = user
            uiThread {
                notifyChangeUser(position)
            }
        }
    }

    override fun openUpdateActivity(
        id: Int,
        firstName: String,
        lastName: String,
        age: Int,
        position: Int
    ) {
        val bundle = Bundle()
        bundle.putInt(EXTRA_ID, id)
        bundle.putString(EXTRA_FIRST_NAME, firstName)
        bundle.putString(EXTRA_LAST_NAME, lastName)
        bundle.putInt(EXTRA_AGE, age)
        bundle.putInt(EXTRA_POSITION, position)
        val intent = Intent(this, RegisterActivity::class.java)
        intent.putExtras(bundle)
        previewRequest.launch(intent)
    }
}