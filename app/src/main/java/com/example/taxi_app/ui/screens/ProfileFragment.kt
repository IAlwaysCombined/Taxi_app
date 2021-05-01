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
        APP_ACTIVITY.title = "Профиль"
    }

    override fun onStart() {
        super.onStart()
        binding.profileExitBtn.setOnClickListener { exitApp() }
        binding.phoneUser.text = PHONE
        binding.nameUser.text = USER.username
    }

    //Exit account
    private fun exitApp() {
        replaceActivity(AuthActivity())
        AUTH.signOut()
    }
}