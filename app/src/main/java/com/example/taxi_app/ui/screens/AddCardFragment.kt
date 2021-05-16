package com.example.taxi_app.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taxi_app.R
import com.example.taxi_app.databinding.FragmentAddCardBinding
import com.example.taxi_app.ui.BaseFragment


class AddCardFragment : BaseFragment(R.layout.fragment_add_card) {

    private lateinit var binding: FragmentAddCardBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddCardBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()
    }
}