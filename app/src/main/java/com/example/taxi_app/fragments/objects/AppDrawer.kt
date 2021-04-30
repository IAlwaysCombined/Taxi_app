package com.example.taxi_app.fragments.objects

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.taxi_app.MainActivity
import com.example.taxi_app.R
import com.example.taxi_app.fragments.screens.MapsFragment
import com.example.taxi_app.fragments.screens.ProfileFragment
import com.example.taxi_app.utilites.replaceFragment
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem

class AppDrawer(var mainActivity: MainActivity,var toolbar: Toolbar) {

    private lateinit var drawer: Drawer
    private lateinit var drawerLayout: DrawerLayout

    fun create() {
        createDrawer()
        drawerLayout = drawer.drawerLayout
    }

    fun disableDrawer() {
        drawer.actionBarDrawerToggle?.isDrawerIndicatorEnabled = false
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        toolbar.setNavigationOnClickListener {
            mainActivity.supportFragmentManager.popBackStack()
        }
    }

    fun enableDrawer() {
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        drawer.actionBarDrawerToggle?.isDrawerIndicatorEnabled = true
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        toolbar.setNavigationOnClickListener {
            drawer.openDrawer()
        }
    }


    private fun createDrawer() {
        drawer = DrawerBuilder()
            .withActivity(mainActivity)
            .withSliderBackgroundColorRes(R.color.colorPrimary)
            .withToolbar(toolbar)
            .withActionBarDrawerToggle(true)
            .withSelectedItem(-1)
            .addDrawerItems(
                PrimaryDrawerItem().withIdentifier(100)
                    .withIconTintingEnabled(true)
                    .withName("Профиль")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_android_black_24dp),
                PrimaryDrawerItem().withIdentifier(101)
                    .withIconTintingEnabled(true)
                    .withName("Способ оплаты")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_android_black_24dp),
                PrimaryDrawerItem().withIdentifier(102)
                    .withIconTintingEnabled(true)
                    .withName("Помощь")
                    .withSelectable(false)
                    .withIcon(R.drawable.ic_android_black_24dp),
            ).withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>
                ): Boolean {
                    when (position) {
                        0 -> mainActivity.replaceFragment(ProfileFragment())
                    }
                    return false
                }
            }).build()
    }
}