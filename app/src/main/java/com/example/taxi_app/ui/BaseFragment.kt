package com.example.taxi_app.ui

import androidx.fragment.app.Fragment
import com.example.taxi_app.utilites.APP_ACTIVITY

//Base fragment
open class BaseFragment(layout: Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.appDrawer.disableDrawer()
    }
    override fun onStop() {
        super.onStop()
        APP_ACTIVITY.appDrawer.enableDrawer()
    }
}