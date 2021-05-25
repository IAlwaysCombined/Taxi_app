@file:Suppress("DEPRECATION")

package com.example.taxi_app.ui.screens.pay

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.taxi_app.R
import com.example.taxi_app.database.*
import com.example.taxi_app.databinding.FragmentPayMethodBinding
import com.example.taxi_app.ui.BaseFragment
import com.example.taxi_app.ui.screens.change_user_data.AddCardFragment
import com.example.taxi_app.utilites.APP_ACTIVITY
import com.example.taxi_app.utilites.replaceFragment
import com.example.taxi_app.utilites.showToast
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.wallet.*


class PayMethodFragment : BaseFragment(R.layout.fragment_pay_method) {

    private lateinit var binding: FragmentPayMethodBinding
    private lateinit var mPaymentsClient: PaymentsClient

    private val LOAD_PAYMENT_DATA_REQUEST_CODE = 991

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPayMethodBinding.bind(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onResume() {
        super.onResume()
        binding.payMethodAddCreditCardBtn.setOnClickListener { addCard() }
        mPaymentsClient = GooglePayUtil.createPaymentsClient(APP_ACTIVITY)
        binding.payMethodTextViewGPay.setOnClickListener { requestPayment() }
        binding.payMethodTextViewCash.setOnClickListener {
            val dateMap = mutableMapOf<String, Any>()
            dateMap[CHILD_PAY_METHOD] = binding.cash.text
            REF_DATABASE_ROOT.child(NODE_USERS).child(UID).updateChildren(dateMap)
        }
        binding.payMethodTextViewCard.setOnClickListener {
            val dateMap = mutableMapOf<String, Any>()
            dateMap[CHILD_PAY_METHOD] = binding.card.text
            REF_DATABASE_ROOT.child(NODE_USERS).child(UID).updateChildren(dateMap)
        }
        GooglePayUtil.isReadyToPay(mPaymentsClient).addOnCompleteListener { task ->
            try {
                setGooglePayButtonAvailable(task.getResult(ApiException::class.java))
            } catch (exception: ApiException) {
                binding.gPay.text = "Payment status: Error init"
                Log.d("isReadyToPay failed", exception.statusCode.toString())
            }
        }

    }

    private fun requestPayment() {
        binding.payMethodTextViewGPay.isClickable = false
        val parameters = PaymentMethodTokenizationParameters.newBuilder()
            .setPaymentMethodTokenizationType(WalletConstants.PAYMENT_METHOD_TOKENIZATION_TYPE_PAYMENT_GATEWAY)
            .addParameter("gateway", "http://www.example.com")
            .addParameter("gatewayMerchantId", "Example Merchant Name")
            .build()
        val transaction = GooglePayUtil.createTransaction("12")
        val request = GooglePayUtil.createPaymentDataRequest(transaction, parameters)
        val futurePaymentData = mPaymentsClient.loadPaymentData(request)

        AutoResolveHelper.resolveTask(futurePaymentData, APP_ACTIVITY, LOAD_PAYMENT_DATA_REQUEST_CODE)
    }

    private fun setGooglePayButtonAvailable(available: Boolean) {
        if (available) {
            binding.gPay.text = "Google pay"
            binding.payMethodTextViewGPay.visibility = View.VISIBLE
        } else {
            binding.gPay.text = "Google pay"
        }
    }

    private fun addCard() {
        replaceFragment(AddCardFragment())
    }

    private fun onPaymentSuccess(paymentData: PaymentData?) {
        showToast("Payment Success")
    }

    private fun onError(statusCode: Int?) {
        Log.w("loadPaymentData failed", String.format("Error code: %d", statusCode))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            LOAD_PAYMENT_DATA_REQUEST_CODE -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        data?.let {
                            onPaymentSuccess(PaymentData.getFromIntent(data))
                        }
                    }
                    Activity.RESULT_CANCELED -> {
                    }
                    AutoResolveHelper.RESULT_ERROR -> {
                        onError(AutoResolveHelper.getStatusFromIntent(data)?.statusCode)
                    }
                }
                binding.payMethodTextViewGPay.isClickable = true
            }
        }
    }
}