package com.example.taxi_app.ui

import androidx.fragment.app.Fragment
import com.example.taxi_app.MainActivity

//Base fragment
open class BaseFragment(layout: Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()
        val parentActivity = activity
        if (parentActivity is MainActivity) parentActivity.appDrawer.disableDrawer()
    }
    override fun onStop() {
        super.onStop()
        val parentActivity = activity
        if (parentActivity is MainActivity) parentActivity.appDrawer.enableDrawer()
    }
}