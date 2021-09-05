package com.jrangel.roomkotlinexample.listeners

interface OnSelectListener {
    fun openUpdateActivity(id: Int, firstName: String, lastName: String, age: Int, position: Int)
}