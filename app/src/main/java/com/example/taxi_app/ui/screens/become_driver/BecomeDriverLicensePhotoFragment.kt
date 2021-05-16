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
import com.example.taxi_app.databinding.FragmentBecomeDriverLicensePhotoBinding
import com.example.taxi_app.ui.BaseFragment
import com.example.taxi_app.utilites.APP_ACTIVITY
import com.example.taxi_app.utilites.downloadAndSetImage
import com.example.taxi_app.utilites.replaceFragment
import com.example.taxi_app.utilites.showToast
import com.theartofdev.edmodo.cropper.CropImage


class BecomeDriverLicensePhotoFragment : BaseFragment(R.layout.fragment_become_driver_license_photo) {

    private lateinit var binding: FragmentBecomeDriverLicensePhotoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBecomeDriverLicensePhotoBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()
        if (BECOMEDRIVER.photo_licence.isNotEmpty()){
            binding.photoLicenseImage.downloadAndSetImage(BECOMEDRIVER.photo_licence)
        }
        binding.becomeDriverPhotoLicenseTextViewDescription.setOnClickListener { setDriverLicensePhoto() }
        binding.becomeDriverPhotoLicenseBtnSaveChanges.setOnClickListener { replaceFragment(BecomeDriverCarPhotoFragment()) }
    }

    private fun setDriverLicensePhoto() {
        CropImage.activity()
            .setRequestedSize(600,1000)
            .start(APP_ACTIVITY, this)
    }

    //Get picture
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
            && resultCode == Activity.RESULT_OK && data != null
        ) {
            val uri = CropImage.getActivityResult(data).uri
            val path = REF_STORAGE_ROOT.child(FOLDER_PHOTO_LICENSE).child(UID)
            putFileToStorage(uri, path) {
                getUrlFromStorage(path) {
                    putUrlToDatabaseBecomeDriverLicense(it) {
                        binding.photoLicenseImage.downloadAndSetImage(it)
                        showToast(getString(R.string.toast_data_update))
                        BECOMEDRIVER.photo_licence = it

                    }
                }
            }
        }
    }

}