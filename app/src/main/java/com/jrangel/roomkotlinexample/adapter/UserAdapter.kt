package com.jrangel.roomkotlinexample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jrangel.roomkotlinexample.R
import com.jrangel.roomkotlinexample.entity.User

class UserAdapter(private val mList: List<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_user, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the text to the textview from our itemHolder class
        holder.tvFirstName.text = ItemsViewModel.firstName
        holder.tvLastName.text = ItemsViewModel.lastName
        holder.tvAge.text = ItemsViewModel.age.toString()
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val ivPerson: ImageView = itemView.findViewById(R.id.imageView)
        val tvFirstName: TextView = itemView.findViewById(R.id.item_tv_first_name)
        val tvLastName: TextView = itemView.findViewById(R.id.item_tv_last_name)
        val tvAge: TextView = itemView.findViewById(R.id.item_tv_age)
    }
}