package com.example.taxi_app.fragments.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taxi_app.R
import com.example.taxi_app.activity.AuthActivity
import com.example.taxi_app.databinding.FragmentProfileBinding
import com.example.taxi_app.fragments.BaseFragment
import com.example.taxi_app.utilites.APP_ACTIVITY
import com.example.taxi_app.utilites.AUTH
import com.example.taxi_app.utilites.replaceActivity


class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        APP_ACTIVITY.title = ""
    }

    override fun onStart() {
        super.onStart()
        binding.profileExitBtn.setOnClickListener { exitApp() }
    }

    private fun exitApp() {
        replaceActivity(AuthActivity())
        AUTH.signOut()
    }
}