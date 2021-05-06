package com.example.taxi_app.ui.screens.become_driver

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taxi_app.R
import com.example.taxi_app.database.*
import com.example.taxi_app.databinding.FragmentBecomeDriverCarNumberBinding
import com.example.taxi_app.ui.BaseFragment
import com.example.taxi_app.utilites.backStack
import com.example.taxi_app.utilites.hideKeyboard
import com.example.taxi_app.utilites.showToast


class BecomeDriverCarNumberFragment : BaseFragment(R.layout.fragment_become_driver_car_number) {

    private lateinit var binding: FragmentBecomeDriverCarNumberBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBecomeDriverCarNumberBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()
        initFields()
        binding.becomeDriverCarNumberBtnSaveChanges.setOnClickListener { saveBecomeDriverCarNumber() }
    }

    private fun saveBecomeDriverCarNumber() {
        val carNumberDriver = binding.becomeDriverCarNumberEdtText.text.toString()
        if (carNumberDriver.isNotEmpty()) {
            val dateMap = mutableMapOf<String, Any>()
            dateMap[CHILD_BECOME_DRIVER_CAR_NUMBER] = carNumberDriver
            REF_DATABASE_ROOT.child(NODE_PRE_ORDER_DRIVERS).child(UID).updateChildren(dateMap)
            showToast(getString(R.string.become_driver_car_number_is_successful_toast))
            backStack()
            hideKeyboard()
        } else{
            showToast(getString(R.string.become_driver_surname_fill_name_toast))
        }
    }

    //Initial fields
    private fun initFields() {
        binding.becomeDriverCarNumberEdtText.setText(BECOMEDRIVER.car_number)
    }
}