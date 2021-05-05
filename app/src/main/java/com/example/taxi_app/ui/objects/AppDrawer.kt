package com.example.taxi_app.ui.objects

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.drawerlayout.widget.DrawerLayout
import com.example.taxi_app.R
import com.example.taxi_app.database.PHONE
import com.example.taxi_app.database.USER
import com.example.taxi_app.ui.screens.BecomeDriverFragment
import com.example.taxi_app.ui.screens.HelpFragment
import com.example.taxi_app.ui.screens.PayMethodFragment
import com.example.taxi_app.ui.screens.ProfileFragment
import com.example.taxi_app.utilites.*
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader


class AppDrawer {

    private lateinit var drawer: Drawer
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var header: AccountHeader
    private lateinit var currentProfile: ProfileDrawerItem

    //Create and init drawer
    fun create() {
        initLoader()
        createHeader()
        createDrawer()
        drawerLayout = drawer.drawerLayout
    }

    //Disable drawer
    fun disableDrawer() {
        drawer.actionBarDrawerToggle?.isDrawerIndicatorEnabled = false
        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        APP_ACTIVITY.toolbar.setNavigationOnClickListener {
            backStack()
            hideKeyboard()
        }
    }

    //Enable drawer
    fun enableDrawer() {
        APP_ACTIVITY.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        drawer.actionBarDrawerToggle?.isDrawerIndicatorEnabled = true
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        APP_ACTIVITY.toolbar.setNavigationOnClickListener {
            drawer.openDrawer()
        }
    }

    //Create drawer
    private fun createDrawer() {
        drawer = DrawerBuilder()
            .withActivity(APP_ACTIVITY)
            .withToolbar(APP_ACTIVITY.toolbar)
            .withActionBarDrawerToggle(true)
            .withSelectedItem(-1)
            .withAccountHeader(header)
            .addDrawerItems(
                PrimaryDrawerItem().withIdentifier(100)
                    .withIconTintingEnabled(true)
                    .withName(APP_ACTIVITY.getString(R.string.profile_description_text_view))
                    .withSelectable(false),
                PrimaryDrawerItem().withIdentifier(101)
                    .withIconTintingEnabled(true)
                    .withName(APP_ACTIVITY.getString(R.string.pay_method_description_text_view))
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
                    changeFragmentReplace(position)
                    return false
                }
            }).build()
    }

    private fun changeFragmentReplace(position: Int){
        when (position) {
            1 -> APP_ACTIVITY.replaceFragment(ProfileFragment())
            2 -> APP_ACTIVITY.replaceFragment(PayMethodFragment())
            3 -> APP_ACTIVITY.replaceFragment(HelpFragment())
            4 -> APP_ACTIVITY.replaceFragment(BecomeDriverFragment())
        }
    }

    //Create header
    private fun createHeader() {
        currentProfile = ProfileDrawerItem()
            .withName(USER.name_user)
            .withEmail(PHONE)
            .withIcon(USER.image_user)
            .withIdentifier(200)
        header = AccountHeaderBuilder()
            .withProfileImagesClickable(false)
            .withActivity(APP_ACTIVITY)
            .withSelectionListEnabledForSingleProfile(false)
            .withHeaderBackground(R.drawable.header)
            .addProfiles(
                currentProfile
            ).build()
    }

    //Update header
    fun updateHeader(){
        currentProfile
            .withName(USER.name_user)
            .withEmail(USER.phone_user)
            .withIcon(USER.image_user)

        header.updateProfile(currentProfile)

    }

    //Init loader for download image in header
    private fun initLoader(){
        DrawerImageLoader.init(object : AbstractDrawerImageLoader(){
            override fun set(imageView: ImageView, uri: Uri, placeholder: Drawable) {
                imageView.downloadAndSetImage(uri.toString())
            }
        })
    }

}