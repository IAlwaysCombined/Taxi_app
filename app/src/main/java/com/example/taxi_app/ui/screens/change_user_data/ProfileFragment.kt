package com.example.taxi_app.ui.screens.change_user_data

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.taxi_app.R
import com.example.taxi_app.activity.AuthActivity
import com.example.taxi_app.database.*
import com.example.taxi_app.databinding.FragmentProfileBinding
import com.example.taxi_app.ui.BaseFragment
import com.example.taxi_app.utilites.*
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView


class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
    }

    override fun onStart() {
        super.onStart()
        initFields()
    }

    override fun onResume() {
        super.onResume()
        initFields()
    }

    //Init fields
    private fun initFields(){
        binding.profilePhoneNumberUserTextView.text = USER.phone_user
        binding.profileNameUserTextView.text = USER.name_user
        binding.profileEmailUserTextView.text = USER.email_user
        binding.profileAvatarImageView.downloadAndSetImage(USER.image_user)
        binding.profileNameUserTextView.setOnClickListener { replaceFragment(ChangeNameFragment()) }
        binding.profileAvatarImageView.setOnClickListener { setImageAvatar() }
        binding.profileExitBtn.setOnClickListener { exitApp() }
    }

    //Set image in circle image
    private fun setImageAvatar() {
        CropImage.activity()
            .setRequestedSize(600,600)
            .setCropShape(CropImageView.CropShape.OVAL)
            .start(APP_ACTIVITY, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
            && resultCode == Activity.RESULT_OK && data != null
        ) {

            val uri = CropImage.getActivityResult(data).uri
            val path = REF_STORAGE_ROOT.child(
                FOLDER_PROFILE_IMAGE
            )
                .child(UID)
            putFileToStorage(uri, path) {
                getUrlFromStorage(path) {
                    putUrlToDatabase(it) {
                        binding.profileAvatarImageView.downloadAndSetImage(it)
                        showToast(getString(R.string.toast_data_update))
                        USER.image_user = it
                        APP_ACTIVITY.appDrawer.updateHeader()
                    }
                }
            }
        }
    }


    //Exit account
    private fun exitApp() {
        replaceActivity(AuthActivity())
        AUTH.signOut()
    }
}