package com.example.taxi_app.ui.screens

import android.os.Bundle
import android.view.View
import com.example.taxi_app.R
import com.example.taxi_app.databinding.FragmentBecomeDriverBinding
import com.example.taxi_app.ui.BaseFragment


class BecomeDriverFragment : BaseFragment(R.layout.fragment_become_driver) {

    private lateinit var binding: FragmentBecomeDriverBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBecomeDriverBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()
    }

}