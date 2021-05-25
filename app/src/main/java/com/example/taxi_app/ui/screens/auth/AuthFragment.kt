package com.example.taxi_app.ui.screens.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.taxi_app.MainActivity
import com.example.taxi_app.R
import com.example.taxi_app.database.AUTH
import com.example.taxi_app.databinding.FragmentAuthBinding
import com.example.taxi_app.utilites.replaceActivity
import com.example.taxi_app.utilites.replaceFragment
import com.example.taxi_app.utilites.showToast


@Suppress("DEPRECATION")
class AuthFragment : Fragment(R.layout.fragment_auth) {

    private lateinit var binding: FragmentAuthBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthBinding.bind(view)
    }

    override fun onStart() {
        super.onStart()
        binding.authBtnEnter.setOnClickListener { changeLoginAndPassword() }
        binding.authTextViewRestorePassword.setOnClickListener { restorePassword() }
        binding.authTextViewRegistration.setOnClickListener { replaceRegistration() }
    }

    private fun replaceRegistration() {
        replaceFragment(RegistrationFragment())
    }

    //Restore password
    private fun restorePassword() {
        val emailAdmin = binding.authEdtTextEmail.text.toString()
        if (emailAdmin.isEmpty()) {
            showToast(getString(R.string.email_edt_text_not_filled_toast))
            return
        }
        val emailAddress = binding.authEdtTextEmail.text.toString()
        AUTH.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) showToast(getString(R.string.message_restore_password_send_toast))
                else showToast(getString(R.string.something_went_wrong_toast))
            }
    }

    //Change login, password, role user
    private fun changeLoginAndPassword() {
        val emailAdmin = binding.authEdtTextEmail.text.toString()
        val passwordAdmin = binding.authEdtTextPassword.text.toString()
        if (emailAdmin.isEmpty()) {
            showToast(getString(R.string.email_edt_text_not_filled_toast))
            return
        } else if (passwordAdmin.isEmpty()) {
            showToast(getString(R.string.password_edt_text_not_filled_toast))
            return
        }
        AUTH.signInWithEmailAndPassword(emailAdmin, passwordAdmin)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    replaceActivity(MainActivity())
                }
                else{
                    showToast(getString(R.string.something_went_wrong_toast))
                    AUTH.signOut()
                }
            }
    }
}