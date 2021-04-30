package com.example.taxi_app.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.taxi_app.R
import com.example.taxi_app.databinding.FragmentPayMethodBinding
import com.example.taxi_app.ui.BaseFragment
import com.example.taxi_app.utilites.APP_ACTIVITY

class PayMethodFragment : BaseFragment(R.layout.fragment_pay_method) {

    private lateinit var binding: FragmentPayMethodBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPayMethodBinding.bind(view)
    }

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.title = "Способ оплаты"
    }
}