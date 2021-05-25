package com.example.taxi_app.ui.screens.become_driver

import android.os.Bundle
import android.view.View
import com.example.taxi_app.R
import com.example.taxi_app.database.*
import com.example.taxi_app.databinding.FragmentBecomeDriverSurnameBinding
import com.example.taxi_app.ui.BaseFragment
import com.example.taxi_app.utilites.backStack
import com.example.taxi_app.utilites.hideKeyboard
import com.example.taxi_app.utilites.showToast

class BecomeDriverSurnameFragment : BaseFragment(R.layout.fragment_become_driver_surname) {

    private lateinit var binding: FragmentBecomeDriverSurnameBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBecomeDriverSurnameBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()
        initFields()
        binding.becomeDriverSurnameBtnSaveChanges.setOnClickListener { saveBecomeDriverSurname() }
    }

    private fun saveBecomeDriverSurname() {
        val surnameDriver = binding.becomeDriverSurnameEdtText.text.toString()
        if (surnameDriver.isNotEmpty()) {
            val dateMap = mutableMapOf<String, Any>()
            dateMap[CHILD_BECOME_DRIVER_SURNAME] = surnameDriver
            REF_DATABASE_ROOT.child(NODE_PRE_ORDER_DRIVERS).child(UID).updateChildren(dateMap)
            showToast(getString(R.string.become_driver_surname_is_successful_toast))
            backStack()
            hideKeyboard()
        } else{
            showToast(getString(R.string.become_driver_surname_fill_name_toast))
        }
    }

    //Initial fields
    private fun initFields() {
        binding.becomeDriverSurnameEdtText.setText(BECOMEDRIVER.surname_driver)
    }
}