package com.example.taxi_app.ui.screens

import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.taxi_app.R
import com.example.taxi_app.databinding.FragmentPayMethodBinding
import com.example.taxi_app.ui.BaseFragment
import com.example.taxi_app.utilites.replaceFragment
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.Wallet
import com.google.android.gms.wallet.WalletConstants


class PayMethodFragment : BaseFragment(R.layout.fragment_pay_method) {

    private lateinit var binding: FragmentPayMethodBinding
    private lateinit var googlePaymentsClient: PaymentsClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPayMethodBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()
        binding.payMethodAddCreditCardBtn.setOnClickListener { addCard() }
        googlePaymentsClient = GooglePaymentUtils.createGoogleApiClientForPay(context)
    }

    private fun addCard() {
        replaceFragment(AddCardFragment())
    }

    object GooglePaymentUtils {
        fun createGoogleApiClientForPay(context: Context?): PaymentsClient =
            Wallet.getPaymentsClient(context!!,
                Wallet.WalletOptions.Builder()
                    .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
                    .setTheme(WalletConstants.THEME_LIGHT)
                    .build())
    }
}