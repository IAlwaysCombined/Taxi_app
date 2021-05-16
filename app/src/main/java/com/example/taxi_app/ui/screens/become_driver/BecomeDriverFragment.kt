package com.example.taxi_app.ui.screens.become_driver

import android.os.Bundle
import android.view.View
import com.example.taxi_app.R
import com.example.taxi_app.database.*
import com.example.taxi_app.databinding.FragmentBecomeDriverBinding
import com.example.taxi_app.ui.BaseFragment
import com.example.taxi_app.utilites.*


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
        binding.becomeDriverCarPhotoTextView.setOnClickListener { replaceFragment(BecomeDriverPhotoFragment()) }
        if (BECOMEDRIVER.photo_car.isNotEmpty() && BECOMEDRIVER.photo_driver.isNotEmpty() && BECOMEDRIVER.photo_licence.isNotEmpty()){
            binding.becomeDriverCarPhotoTextView.text = getString(R.string.toast_data_update)
            binding.becomeDriverBtnSendChanges.setOnClickListener { sendOrder() }
        }
    }

    private fun sendOrder() {
        val dateMap = mutableMapOf<String, Any>()
        dateMap[CHILD_BECOME_DRIVER_UID] = UID
        dateMap[CHILD_BECOME_DRIVER_NAME] = BECOMEDRIVER.name_driver
        dateMap[CHILD_BECOME_DRIVER_SURNAME] = BECOMEDRIVER.surname_driver
        dateMap[CHILD_BECOME_DRIVER_LAST_NAME] = BECOMEDRIVER.last_name_driver
        dateMap[CHILD_BECOME_DRIVER_CAR_NUMBER] = BECOMEDRIVER.car_number
        dateMap[CHILD_BECOME_DRIVER_CAR] = BECOMEDRIVER.car
        dateMap[CHILD_BECOME_DRIVER_PHONE] = PHONE
        dateMap[CHILD_DRIVER_PHOTO] = BECOMEDRIVER.photo_driver
        dateMap[CHILD_PHOTO_LICENCE] = BECOMEDRIVER.photo_licence
        dateMap[CHILD_PHOTO_CAR] = BECOMEDRIVER.photo_car
        REF_DATABASE_ROOT.child(NODE_ORDER_DRIVERS).child(UID).updateChildren(dateMap)
        REF_DATABASE_ROOT.child(NODE_PRE_ORDER_DRIVERS).child(UID).removeValue()
        showToast(getString(R.string.become_driver_toast_orders_done))
        restartActivity()
        hideKeyboard()
    }
}