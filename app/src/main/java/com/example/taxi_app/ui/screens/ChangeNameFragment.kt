package com.example.taxi_app.ui.screens

import android.os.Bundle
import android.view.View
import com.example.taxi_app.R
import com.example.taxi_app.database.*
import com.example.taxi_app.databinding.FragmentChangeNameBinding
import com.example.taxi_app.ui.BaseFragment
import com.example.taxi_app.utilites.*

class ChangeNameFragment : BaseFragment(R.layout.fragment_change_name) {

    private lateinit var binding: FragmentChangeNameBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChangeNameBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()
        initFields()
        binding.changeNameBtnSaveChanges.setOnClickListener { saveUserName() }
    }

    //Save user name changes
    private fun saveUserName() {
        val userName = binding.changeNameEdtText.text.toString()
        if (userName.isNotEmpty()){
            val dateMap = mutableMapOf<String, Any>()
            dateMap[CHILD_NAME] = userName
            REF_DATABASE_ROOT.child(NODE_USERS).child(UID).updateChildren(dateMap)
            APP_ACTIVITY.appDrawer.updateHeader()
            showToast(getString(R.string.change_user_name_is_successful_toast))
            backStack()
            hideKeyboard()
        }
        else{
            showToast(getString(R.string.change_user_name_fill_name_toast))
        }
    }

    //Initial fields
    private fun initFields() {
        binding.changeNameEdtText.setText(USER.name_user)
    }
}