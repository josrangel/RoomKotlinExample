package com.jrangel.roomkotlinexample.listeners

import com.jrangel.roomkotlinexample.entity.User

interface OnDeleteListener {
    fun deleteElement(user: User, position: Int)
}