package com.example.taxi_app.ui.screens.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.taxi_app.MainActivity
import com.example.taxi_app.R
import com.example.taxi_app.database.*
import com.example.taxi_app.databinding.FragmentRegistrationBinding
import com.example.taxi_app.utilites.replaceActivity
import com.example.taxi_app.utilites.replaceFragment
import com.example.taxi_app.utilites.showToast


class RegistrationFragment: Fragment(R.layout.fragment_registration) {

    private lateinit var binding: FragmentRegistrationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegistrationBinding.bind(view)
    }

    override fun onStart() {
        super.onStart()
        binding.registrationTextViewBackAuth.setOnClickListener { backAuth() }
        binding.registrationBtnEnter.setOnClickListener { registrationBtn() }
    }

    private fun registrationBtn() {
        val emailUser = binding.registrationEdtTextEmail.text.toString()
        val passwordUser = binding.registrationEdtTextPassword.text.toString()
        val phoneUser = binding.registrationEdtTextPhone.text.toString()
        val nameUser = binding.registrationEdtTextName.text.toString()
        if (emailUser.isEmpty()){
            showToast(getString(R.string.email_edt_text_not_filled_toast))
            return
        }
        if (passwordUser.isEmpty()){
            showToast(getString(R.string.password_edt_text_not_filled_toast))
            return
        }
        if (phoneUser.isEmpty()){
            showToast(getString(R.string.phone_edt_text_not_filled_toast))
            return
        }
        if (nameUser.isEmpty()){
            showToast(getString(R.string.name_edt_text_not_filled_toast))
            return
        }
        else{
            AUTH.createUserWithEmailAndPassword(emailUser, passwordUser)
                .addOnCompleteListener {
                    val uid = AUTH.currentUser?.uid.toString()
                    val dateMap = mutableMapOf<String, Any>()
                    dateMap[CHILD_EMAIL] = emailUser
                    dateMap[CHILD_PHONE] = phoneUser
                    dateMap[CHILD_NAME] = nameUser
                    dateMap[CHILD_ROLE] = USER_ROLE
                    REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dateMap)
                        .addOnCompleteListener {task ->
                            if(task.isSuccessful) {
                                replaceActivity(MainActivity())
                                showToast(getString(R.string.welcome_toast))
                            }
                            else showToast(getString(R.string.something_went_wrong_toast))
                        }
                }
        }
    }

    private fun backAuth() {
        replaceFragment(AuthFragment())
    }
}