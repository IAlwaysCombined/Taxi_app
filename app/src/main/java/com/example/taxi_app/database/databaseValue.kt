package com.example.taxi_app.database

import com.example.taxi_app.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference

lateinit var AUTH: FirebaseAuth
lateinit var UID: String
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var REF_STORAGE_ROOT: StorageReference
lateinit var USER: UserModel
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
const val FOLDER_PROFILE_IMAGE = "profile_image"
const val CHILD_PHOTO_URL = "image_user"

//Role user
const val USER_ROLE = "user"
const val DRIVER_ROLE = "driver"
