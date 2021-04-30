package com.example.taxi_app.fragments.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.taxi_app.R
import com.example.taxi_app.databinding.FragmentEnterPhoneNumberBinding
import com.example.taxi_app.utilites.*
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


@Suppress("DEPRECATION")
class EnterPhoneNumberFragment : Fragment(R.layout.fragment_enter_phone_number) {

    private lateinit var phoneNumber: String
    private lateinit var callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var binding: FragmentEnterPhoneNumberBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEnterPhoneNumberBinding.bind(view)
    }

    override fun onStart() {
        super.onStart()

        //Callback return result auth
        callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            //Login completed
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showToast("Добро пожаловать")
                        restartActivity()
                    } else showToast(task.exception?.message.toString())
                }
            }

            //Login error
            override fun onVerificationFailed(p0: FirebaseException) {
                showToast(p0.message.toString())
            }

            //Login for the first time
            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                replaceFragment(
                    EnterCodeFragment(
                        phoneNumber,
                        id
                    )
                )
            }
        }
        binding.registerBtn.setOnClickListener { sendCode() }
    }

    //Change edit number phone
    private fun sendCode() {
        if (binding.registerEdtTextEnterPhoneNumber.text.toString().isEmpty()) {
            showToast("Введите номер телефона")
        } else {
            authUser()
        }
    }

    //Init auth user
    private fun authUser() {
        phoneNumber = binding.registerEdtTextEnterPhoneNumber.text.toString()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber,
            60,
            TimeUnit.SECONDS,
            APP_ACTIVITY,
            callback
        )
    }
}