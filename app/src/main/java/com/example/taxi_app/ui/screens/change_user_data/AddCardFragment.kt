package com.example.taxi_app.ui.screens.change_user_data

import android.os.Bundle
import android.view.View
import com.example.taxi_app.R
import com.example.taxi_app.database.*
import com.example.taxi_app.databinding.FragmentAddCardBinding
import com.example.taxi_app.ui.BaseFragment
import com.example.taxi_app.utilites.showToast


class AddCardFragment : BaseFragment(R.layout.fragment_add_card) {

    private lateinit var binding: FragmentAddCardBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddCardBinding.bind(view)
    }

    override fun onResume() {
        super.onResume()
        binding.addCardBtn.setOnClickListener { addCardData() }
    }

    private fun addCardData() {
        val numberCard = binding.addCardNumber.text.toString()
        val dataCard = binding.addCardData.text.toString()
        val cvvCard = binding.addCardCvv.text.toString()
        if (numberCard.isEmpty()){
            showToast("Заполните номер карты")
            return
        }
        if (dataCard.isEmpty()){
            showToast("Заполните номер карты")
            return
        }
        if (cvvCard.isEmpty()){
            showToast("Заполните номер карты")
            return
        }
        else{
            val dateMap = mutableMapOf<String, Any>()
            dateMap[CHILD_NUMBER] = numberCard
            dateMap[CHILD_DATA] = dataCard
            dateMap[CHILD_CVV] = cvvCard
            REF_DATABASE_ROOT.child(NODE_CARDS).child(UID).updateChildren(dateMap)
            showToast("Карта добавлена")
        }
    }
}