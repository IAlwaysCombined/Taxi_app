package com.example.taxi_app.ui.screens.become_driver

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taxi_app.R
import com.example.taxi_app.database.*
import com.example.taxi_app.databinding.FragmentBecomeDriverCarPhotoBinding
import com.example.taxi_app.ui.BaseFragment
import com.example.taxi_app.utilites.*
import com.theartofdev.edmodo.cropper.CropImage

class BecomeDriverCarPhotoFragment : BaseFragment(R.layout.fragment_become_driver_car_photo) {

    private lateinit var binding: FragmentBecomeDriverCarPhotoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBecomeDriverCarPhotoBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()
        if (BECOMEDRIVER.photo_car.isNotEmpty()){
            binding.photoCarImage.downloadAndSetImage(BECOMEDRIVER.photo_car)
        }
        binding.becomeDriverPhotoCarTextViewDescription.setOnClickListener { setDriverCarPhoto() }
        binding.becomeDriverPhotoCarBtnSaveChanges.setOnClickListener { replaceFragment(BecomeDriverFragment()) }
    }

    private fun setDriverCarPhoto() {
        CropImage.activity()
            .setRequestedSize(1000,600)
            .start(APP_ACTIVITY, this)
    }

    //Get picture
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
            && resultCode == Activity.RESULT_OK && data != null
        ) {
            val uri = CropImage.getActivityResult(data).uri
            val path = REF_STORAGE_ROOT.child(FOLDER_PHOTO_CAR).child(UID)
            putFileToStorage(uri, path) {
                getUrlFromStorage(path) {
                    putUrlToDatabaseBecomeDriverCar(it) {
                        binding.photoCarImage.downloadAndSetImage(it)
                        showToast(getString(R.string.toast_data_update))
                        BECOMEDRIVER.photo_car = it
                    }
                }
            }
        }
    }
}