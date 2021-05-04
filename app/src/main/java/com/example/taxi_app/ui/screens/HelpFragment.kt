package com.example.taxi_app.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taxi_app.R
import com.example.taxi_app.databinding.FragmentHelpBinding
import com.example.taxi_app.ui.BaseFragment
import com.example.taxi_app.utilites.APP_ACTIVITY

class HelpFragment : BaseFragment(R.layout.fragment_help) {

    private lateinit var binding: FragmentHelpBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHelpBinding.bind(view)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }
}