package com.example.taxi_app.ui.objects

import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import com.example.taxi_app.R
import com.example.taxi_app.ui.screens.HelpFragment
import com.example.taxi_app.ui.screens.PayMethodFragment
import com.example.taxi_app.ui.screens.ProfileFragment
import com.example.taxi_app.utilites.APP_ACTIVITY
import com.example.taxi_app.utilites.PHONE
import com.example.taxi_app.utilites.initFirebase
import com.example.taxi_app.utilites.replaceFragment
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import kotlinx.android.synthetic.main.activity_main.*

class AppDrawer() {

    private lateinit var drawer: Drawer
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var header: AccountHeader
    private lateinit var currentProfile: ProfileDrawerItem

    //Create and init drawer
    fun create() {
        initFirebase()
        createHeader()
        createDrawer()
        drawerLayout = drawer.drawerLayout
    }

    //Disable drawer
    fun disableDrawer() {
        drawer.actionBarDrawerToggle?.isDrawerIndicatorEnabled = false
        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        APP_ACTIVITY.main_toolbar.setNavigationOnClickListener {
            APP_ACTIVITY.supportFragmentManager.popBackStack()
        }
    }

    //Enable drawer
    fun enableDrawer() {
        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        drawer.actionBarDrawerToggle?.isDrawerIndicatorEnabled = true
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        APP_ACTIVITY.main_toolbar.setNavigationOnClickListener {
            drawer.openDrawer()
        }
    }

    //Create header
    private fun createHeader() {
        currentProfile = ProfileDrawerItem()
            .withIcon(R.mipmap.ic_launcher)
            .withEmail(PHONE)
        header = AccountHeaderBuilder()
            .withActivity(APP_ACTIVITY)
            .withHeaderBackground(R.drawable.header)
            .addProfiles(
                currentProfile
            ).build()
    }

    //Create drawer
    private fun createDrawer() {
        drawer = DrawerBuilder()
            .withActivity(APP_ACTIVITY)
            .withAccountHeader(header)
            .withSliderBackgroundColorRes(R.color.white)
            .withToolbar(APP_ACTIVITY.main_toolbar)
            .withActionBarDrawerToggle(true)
            .withSelectedItem(-1)
            .addDrawerItems(
                PrimaryDrawerItem().withIdentifier(100)
                    .withIconTintingEnabled(true)
                    .withName("Профиль")
                    .withSelectable(false),
                PrimaryDrawerItem().withIdentifier(101)
                    .withIconTintingEnabled(true)
                    .withName("Способ оплаты")
                    .withSelectable(false),
                PrimaryDrawerItem().withIdentifier(102)
                    .withIconTintingEnabled(true)
                    .withName("Служба поддержки")
                    .withSelectable(false),
                PrimaryDrawerItem().withIdentifier(103)
                    .withIconTintingEnabled(true)
                    .withName("Стать водителем")
                    .withSelectable(false),
            ).withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>
                ): Boolean {
                    when (position) {
                        1 -> APP_ACTIVITY.replaceFragment(ProfileFragment())
                        2 -> APP_ACTIVITY.replaceFragment(PayMethodFragment())
                        3 -> APP_ACTIVITY.replaceFragment(HelpFragment())
                    }
                    return false
                }
            }).build()
    }
}