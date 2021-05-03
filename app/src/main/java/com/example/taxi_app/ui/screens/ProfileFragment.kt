package com.example.taxi_app.ui.screens

import android.os.Bundle
import android.view.View
import com.example.taxi_app.R
import com.example.taxi_app.activity.AuthActivity
import com.example.taxi_app.databinding.FragmentProfileBinding
import com.example.taxi_app.ui.BaseFragment
import com.example.taxi_app.utilites.*


class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        APP_ACTIVITY.title = ""
    }

    override fun onStart() {
        super.onStart()
        initFields()
    }

    //Init fields
    private fun initFields(){
        binding.profileExitBtn.setOnClickListener { exitApp() }
        binding.profilePhoneUserTextViewPhone.text = PHONE
        binding.profileNameUserTextViewName.text = USER.username
        binding.profilePhoneUserTextViewPhone.setOnClickListener { showToast("Хуй") }
        binding.profileNameUserTextViewName.setOnClickListener {  }
    }

    //Exit account
    private fun exitApp() {
        replaceActivity(AuthActivity())
        AUTH.signOut()
    }
}