package com.example.taxi_app.ui.screens.become_driver

import android.os.Bundle
import android.view.View
import com.example.taxi_app.R
import com.example.taxi_app.database.*
import com.example.taxi_app.databinding.FragmentBecomeDriverNameBinding
import com.example.taxi_app.ui.BaseFragment
import com.example.taxi_app.utilites.backStack
import com.example.taxi_app.utilites.hideKeyboard
import com.example.taxi_app.utilites.showToast


class BecomeDriverNameFragment: BaseFragment(R.layout.fragment_become_driver_name){

    private lateinit var binding: FragmentBecomeDriverNameBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBecomeDriverNameBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()
        initFields()
        binding.becomeDriverNameBtnSaveChanges.setOnClickListener { saveBecomeDriverName() }
    }

    private fun saveBecomeDriverName() {
        val nameDriver = binding.becomeDriverNameEdtText.text.toString()
        if (nameDriver.isNotEmpty()) {
            val dateMap = mutableMapOf<String, Any>()
            dateMap[CHILD_BECOME_DRIVER_NAME] = nameDriver
            REF_DATABASE_ROOT.child(NODE_PRE_ORDER_DRIVERS).child(UID).updateChildren(dateMap)
            showToast(getString(R.string.become_driver_name_is_successful_toast))
            backStack()
            hideKeyboard()
        } else{
            showToast(getString(R.string.become_driver_name_fill_name_toast))
        }
    }

    //Initial fields
    private fun initFields() {
        binding.becomeDriverNameEdtText.setText(BECOMEDRIVER.name_driver)
    }
}


