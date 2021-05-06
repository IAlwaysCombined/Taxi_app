package com.example.taxi_app.ui.screens.become_driver

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import com.example.taxi_app.MainActivity
import com.example.taxi_app.R
import com.example.taxi_app.database.*
import com.example.taxi_app.databinding.FragmentBecomeDriverPhotoBinding
import com.example.taxi_app.ui.BaseFragment
import com.example.taxi_app.utilites.*
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView


@Suppress("DEPRECATION")
class BecomeDriverPhotoFragment : BaseFragment(R.layout.fragment_become_driver_photo) {

    private lateinit var binding: FragmentBecomeDriverPhotoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBecomeDriverPhotoBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()
        if (BECOMEDRIVER.photo_driver.isNotEmpty()){
            binding.photoDriverImage.downloadAndSetImage(BECOMEDRIVER.photo_driver)
        }
        binding.becomeDriverPhotoTextViewDescription.setOnClickListener { setDriverPhoto() }
        binding.becomeDriverPhotoBtnSaveChanges.setOnClickListener { replaceFragment(BecomeDriverLicensePhotoFragment()) }
    }

    private fun setDriverPhoto() {
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
            val path = REF_STORAGE_ROOT.child(FOLDER_PHOTO_DRIVER).child(UID)
            putFileToStorage(uri, path) {
                getUrlFromStorage(path) {
                    putUrlToDatabaseBecomeDriver(it) {
                        binding.photoDriverImage.downloadAndSetImage(it)
                        showToast(getString(R.string.toast_data_update))
                        BECOMEDRIVER.photo_driver = it

                    }
                }
            }
        }
    }
}