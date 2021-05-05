package com.example.taxi_app.utilites

import com.example.taxi_app.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

lateinit var AUTH: FirebaseAuth
lateinit var UID: String
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var USER: User
lateinit var PHONE: String

//Nodes
const val NODE_USERS = "users"
const val NODE_PHONES = "phones"

//User const
const val CHILD_ID = "id"
const val CHILD_PHONE = "phone_user"
const val CHILD_ROLE = "role"
const val CHILD_NAME = "name_user"
const val CHILD_EMAIL = "email_user"

//Role user
const val USER_ROLE = "user"
const val DRIVER_ROLE = "driver"

//Init firebase help method
fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    USER = User()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    UID = AUTH.currentUser?.uid.toString()
    PHONE = AUTH.currentUser?.phoneNumber.toString()
}

//Initial Users
inline fun initUser(crossinline function: () -> Unit) {
    /* Функция высшего порядка, инициализация текущей модели USER */
    REF_DATABASE_ROOT.child(NODE_USERS).child(UID)
        .addListenerForSingleValueEvent(AppValueEventListener {
            USER = it.getValue(User::class.java) ?: User()
            function()
        })
}