package com.example.taxi_app.database

import android.net.Uri
import com.example.taxi_app.models.BecomeDriverModel
import com.example.taxi_app.models.DriverModel
import com.example.taxi_app.models.RidesModel
import com.example.taxi_app.models.UserModel
import com.example.taxi_app.utilites.AppValueEventListener
import com.example.taxi_app.utilites.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

//Init firebase help method
fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    USER = UserModel()
    BECOMEDRIVER = BecomeDriverModel()
    RIDES = RidesModel()
    DRIVER = DriverModel()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    REF_STORAGE_ROOT = FirebaseStorage.getInstance().reference
    UID = AUTH.currentUser?.uid.toString()
    EMAIL = AUTH.currentUser?.email.toString()
}

//Initial Users
inline fun initUser(crossinline function: () -> Unit) {
    REF_DATABASE_ROOT.child(NODE_USERS).child(UID)
        .addListenerForSingleValueEvent(AppValueEventListener {
            USER = it.getValue(UserModel::class.java) ?: UserModel()
            function()
        })
}

////Initial Driver
fun initDriver(){
    REF_DATABASE_ROOT.child(NODE_DRIVER)
        .addListenerForSingleValueEvent(AppValueEventListener{
            DRIVER = it.getValue(DriverModel::class.java) ?: DriverModel()
        })
}

//Initial Become Driver
fun initBecomeDriver() {
    REF_DATABASE_ROOT.child(NODE_PRE_ORDER_DRIVERS).child(UID)
        .addValueEventListener(AppValueEventListener {
            BECOMEDRIVER = it.getValue(BecomeDriverModel::class.java) ?: BecomeDriverModel()
        })
}

//Send URL in realtime database
inline fun putUrlToDatabase(url: String, crossinline function: () -> Unit) {
    REF_DATABASE_ROOT.child(NODE_USERS).child(UID)
        .child(CHILD_PHOTO_USER).setValue(url)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }
}

//Send URL in realtime database
inline fun putUrlToDatabaseBecomeDriver(url: String, crossinline function: () -> Unit) {
    REF_DATABASE_ROOT.child(NODE_PRE_ORDER_DRIVERS).child(UID)
        .child(FOLDER_PHOTO_DRIVER).setValue(url)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }
}

//Send URL in realtime database
inline fun putUrlToDatabaseBecomeDriverCar(url: String, crossinline function: () -> Unit) {
    REF_DATABASE_ROOT.child(NODE_PRE_ORDER_DRIVERS).child(UID)
        .child(FOLDER_PHOTO_CAR).setValue(url)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }
}

//Send URL in realtime database
inline fun putUrlToDatabaseBecomeDriverLicense(url: String, crossinline function: () -> Unit) {
    REF_DATABASE_ROOT.child(NODE_PRE_ORDER_DRIVERS).child(UID)
        .child(FOLDER_PHOTO_LICENSE).setValue(url)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }
}

//Get URL image in storage
inline fun getUrlFromStorage(path: StorageReference, crossinline function: (url: String) -> Unit) {
    path.downloadUrl
        .addOnSuccessListener { function(it.toString()) }
        .addOnFailureListener { showToast(it.message.toString()) }
}

//Send image in storage
inline fun putFileToStorage(uri: Uri, path: StorageReference, crossinline function: () -> Unit) {
    path.putFile(uri)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }
}
