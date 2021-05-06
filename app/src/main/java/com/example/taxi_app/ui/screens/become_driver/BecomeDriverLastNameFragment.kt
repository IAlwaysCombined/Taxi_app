package com.example.taxi_app.ui.screens.become_driver

import android.os.Bundle
import android.view.View
import com.example.taxi_app.R
import com.example.taxi_app.database.*
import com.example.taxi_app.databinding.FragmentBecomeDriverLastNameBinding
import com.example.taxi_app.ui.BaseFragment
import com.example.taxi_app.utilites.backStack
import com.example.taxi_app.utilites.hideKeyboard
import com.example.taxi_app.utilites.showToast


class BecomeDriverLastNameFragment : BaseFragment(R.layout.fragment_become_driver_last_name) {

    private lateinit var binding: FragmentBecomeDriverLastNameBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBecomeDriverLastNameBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()
        initFields()
        binding.becomeDriverLastNameBtnSaveChanges.setOnClickListener { saveBecomeDriverLastName() }
    }

    private fun saveBecomeDriverLastName() {
        val lastNameDriver = binding.becomeDriverLastNameEdtText.text.toString()
        if (lastNameDriver.isNotEmpty()) {
            val dateMap = mutableMapOf<String, Any>()
            dateMap[CHILD_BECOME_DRIVER_LAST_NAME] = lastNameDriver
            REF_DATABASE_ROOT.child(NODE_PRE_ORDER_DRIVERS).child(UID).updateChildren(dateMap)
            showToast(getString(R.string.become_driver_last_name_is_successful_toast))
            backStack()
            hideKeyboard()
        } else{
            showToast(getString(R.string.become_driver_last_name_fill_name_toast))
        }
    }

    //Initial fields
    private fun initFields() {
        binding.becomeDriverLastNameEdtText.setText(BECOMEDRIVER.last_name_driver)
    }
}