package com.example.taxi_app.ui.screens.become_driver

import android.os.Bundle
import android.view.View
import com.example.taxi_app.R
import com.example.taxi_app.database.BECOMEDRIVER
import com.example.taxi_app.databinding.FragmentBecomeDriverBinding
import com.example.taxi_app.ui.BaseFragment
import com.example.taxi_app.utilites.replaceFragment


class BecomeDriverFragment: BaseFragment(R.layout.fragment_become_driver) {

    private lateinit var binding: FragmentBecomeDriverBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBecomeDriverBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()
        binding.becomeDriverNameTextView.setOnClickListener { replaceFragment(BecomeDriverNameFragment()) }
        binding.becomeDriverNameTextView.text = BECOMEDRIVER.name_driver
        binding.becomeDriverSurnameTextView.setOnClickListener { replaceFragment(BecomeDriverSurnameFragment()) }
        binding.becomeDriverSurnameTextView.text = BECOMEDRIVER.surname_driver
        binding.becomeDriverLastNameTextView.setOnClickListener { replaceFragment(BecomeDriverLastNameFragment()) }
        binding.becomeDriverLastNameTextView.text = BECOMEDRIVER.last_name_driver
        binding.becomeDriverCarTextView.setOnClickListener { replaceFragment(BecomeDriverCarFragment()) }
        binding.becomeDriverCarTextView.text = BECOMEDRIVER.car
        binding.becomeDriverCarNumberTextView.setOnClickListener { replaceFragment(BecomeDriverCarNumberFragment()) }
        binding.becomeDriverCarNumberTextView.text = BECOMEDRIVER.car_number
    }
}