package com.example.taxi_app.ui.screens

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import com.example.taxi_app.R
import com.example.taxi_app.databinding.FragmentPayMethodBinding
import com.example.taxi_app.ui.BaseFragment


class PayMethodFragment : BaseFragment(R.layout.fragment_pay_method) {

    private lateinit var binding: FragmentPayMethodBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPayMethodBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()
    }
}