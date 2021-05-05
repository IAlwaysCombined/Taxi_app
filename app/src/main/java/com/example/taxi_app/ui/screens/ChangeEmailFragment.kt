package com.example.taxi_app.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taxi_app.R
import com.example.taxi_app.databinding.FragmentChangeEmailBinding
import com.example.taxi_app.ui.BaseFragment
import com.example.taxi_app.utilites.*


class ChangeEmailFragment : BaseFragment(R.layout.fragment_change_email) {

    private lateinit var binding: FragmentChangeEmailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChangeEmailBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()
        initFields()
        binding.changeEmailBtnSaveChanges.setOnClickListener { saveUserEmail() }
    }

    private fun saveUserEmail() {
        val userEmail = binding.changeEmailEdtText.text.toString()
        if (userEmail.isNotEmpty()){
            val dateMap = mutableMapOf<String, Any>()
            dateMap[CHILD_EMAIL] = userEmail
            REF_DATABASE_ROOT.child(NODE_USERS).child(UID).updateChildren(dateMap)
            APP_ACTIVITY.appDrawer.updateHeader()
            showToast(getString(R.string.change_user_email_is_successful_toast))
            backStack()
            hideKeyboard()
        }
        else{
            showToast(getString(R.string.change_user_email_fill_email_toast))
        }
    }

    //Initial fields
    private fun initFields() {
        binding.changeEmailEdtText.setText(USER.email_user)
    }
}