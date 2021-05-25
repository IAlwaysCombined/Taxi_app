package com.example.taxi_app.database

import com.example.taxi_app.models.BecomeDriverModel
import com.example.taxi_app.models.DriverModel
import com.example.taxi_app.models.RidesModel
import com.example.taxi_app.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import java.sql.Driver

lateinit var AUTH: FirebaseAuth
lateinit var UID: String
lateinit var EMAIL: String
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var REF_STORAGE_ROOT: StorageReference
lateinit var USER: UserModel
lateinit var BECOMEDRIVER: BecomeDriverModel
lateinit var RIDES: RidesModel
lateinit var DRIVER: DriverModel

//Nodes
const val NODE_USERS = "users"
const val NODE_ORDER_DRIVERS = "order_drivers"
const val NODE_PRE_ORDER_DRIVERS = "pre_order_drivers"
const val NODE_PHONES = "phones"
const val NODE_ORDER_RIDES = "order_rides"
const val NODE_CARDS = "cards"
const val NODE_RIDES = "rides"
const val NODE_PRE_RIDES = "pre_rides"
const val NODE_KEY_RIDE = "key_ride"
const val NODE_DRIVER = "driver"

//Folder
const val FOLDER_PROFILE_IMAGE = "profile_image"
const val FOLDER_PHOTO_DRIVER = "photo_driver"
const val FOLDER_PHOTO_LICENSE = "photo_licence"
const val FOLDER_PHOTO_CAR = "photo_car"

//User const
const val CHILD_ID = "id"
const val CHILD_PHONE = "phone_user"
const val CHILD_ROLE = "role"
const val CHILD_NAME = "name_user"
const val CHILD_EMAIL = "email_user"
const val CHILD_PHOTO_USER = "image_user"
const val CHILD_PAY_METHOD = "pay_method"
const val CHILD_NUMBER = "number_card"
const val CHILD_DATA = "data_card"
const val CHILD_CVV = "cvv_card"
const val CHILD_START = "start_ride"
const val CHILD_END = "end_ride"
const val CHILD_CENTER = "center_ride"
const val CHILD_COAST_RIDE = "coast_ride"
const val CHILD_KEY_RIDE = "key_ride"

//Driver const
const val CHILD_NAME_DRIVER = "name_driver"



//Become driver const
const val CHILD_BECOME_DRIVER_UID = "uid"
const val CHILD_BECOME_DRIVER_EMAIL = "email_driver"
const val CHILD_BECOME_DRIVER_CAR = "car"
const val CHILD_BECOME_DRIVER_CAR_NUMBER = "car_number"
const val CHILD_BECOME_DRIVER_NAME = "name_driver"
const val CHILD_BECOME_DRIVER_SURNAME = "surname_driver"
const val CHILD_BECOME_DRIVER_LAST_NAME = "last_name_driver"
const val CHILD_BECOME_DRIVER_PHONE = "phone_number_driver"
const val CHILD_DRIVER_PHOTO = "photo_driver"
const val CHILD_PHOTO_CAR = "photo_car"
const val CHILD_PHOTO_LICENCE = "photo_licence"

//Role user
const val USER_ROLE = "user"
const val DRIVER_ROLE = "driver"
