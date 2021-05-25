@file:Suppress("DEPRECATION")

package com.example.taxi_app.ui.screens.pay

import android.app.Activity
import android.provider.SyncStateContract
import com.google.android.gms.tasks.Task
import com.google.android.gms.wallet.*
import org.json.JSONObject
import java.math.BigDecimal

object GooglePayUtil {
    fun createPaymentsClient(activity: Activity): PaymentsClient {
        val walletOptions = Wallet.WalletOptions.Builder()
            .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
            .build()
        return Wallet.getPaymentsClient(activity, walletOptions)
    }

    private val SUPPORTED_METHODS = listOf(
        WalletConstants.PAYMENT_METHOD_CARD,
        WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD
    )

    fun isReadyToPay(client: PaymentsClient): Task<Boolean> {
        val request = IsReadyToPayRequest.newBuilder()
        for (allowedMethod in SUPPORTED_METHODS) {
            request.addAllowedPaymentMethod(allowedMethod)
        }
        return client.isReadyToPay(request.build())
    }

    fun createTransaction(price: String): TransactionInfo {
        return TransactionInfo.newBuilder()
            .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
            .setTotalPrice(price)
            .setCurrencyCode("RUB")
            .build()
    }

    private val SHIPPING_SUPPORTED_COUNTRIES = listOf("UA, US, DE, GB")

    private val SUPPORTED_NETWORKS = listOf(
        WalletConstants.CARD_NETWORK_VISA,
        WalletConstants.CARD_NETWORK_MASTERCARD
    )

    fun createPaymentDataRequest(
        transactionInfo: TransactionInfo,
        params: PaymentMethodTokenizationParameters,
    ): PaymentDataRequest {

        return PaymentDataRequest.newBuilder()
            .setPhoneNumberRequired(false)
            .setEmailRequired(true)
            .setShippingAddressRequired(true)
            .setShippingAddressRequirements(
                ShippingAddressRequirements.newBuilder()
                    .addAllowedCountryCodes(SHIPPING_SUPPORTED_COUNTRIES)
                    .build()
            )
            .setTransactionInfo(transactionInfo)
            .addAllowedPaymentMethods(SUPPORTED_METHODS)
            .setCardRequirements(
                CardRequirements.newBuilder()
                    .addAllowedCardNetworks(SUPPORTED_NETWORKS)
                    .setAllowPrepaidCards(true)
                    .setBillingAddressFormat(WalletConstants.BILLING_ADDRESS_FORMAT_FULL)
                    .build()
            )
            .setPaymentMethodTokenizationParameters(params)
            .setUiRequired(true)
            .build()
    }
}