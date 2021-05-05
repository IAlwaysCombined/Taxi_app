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
        binding.profileExitBtn.setOnClickListener { exitApp() }
        binding.profilePhoneNumberUserTextView.text = PHONE
        binding.profileNameUserTextView.text = USER.name_user
        binding.profileEmailUserTextView.text = USER.email_user
        binding.profileNameUserTextView.setOnClickListener { replaceFragment(ChangeNameFragment()) }
        binding.profilePhoneNumberUserTextView.setOnClickListener {  }
        binding.profileEmailUserTextView.setOnClickListener { replaceFragment(ChangeEmailFragment()) }
    }

    //Exit account
    private fun exitApp() {
        replaceActivity(AuthActivity())
        AUTH.signOut()
    }
}