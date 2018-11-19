package com.eltonkola.tabsapp.ui

import android.app.Activity
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.eltonkola.arkitekt.AppScreen
import com.eltonkola.arkitekt.annotations.ScreenView
import com.eltonkola.arkitekt.views.AppScreenContainer
import com.eltonkola.config.AppScreens
import com.eltonkola.tabsapp.R
import com.eltonkola.tabsapp.model.Website
import kotterknife.bindView

@ScreenView(path = "/")
class MainScreen : AppScreen<Void>() {

    override val view: Int get() = R.layout.screen_main

    override val menu: Int?  = R.menu.main_menu

    val nav_view: NavigationView by bindView(R.id.nav_view)
    val navigation: BottomNavigationView by bindView(R.id.navigation)
    val drawer_layout: DrawerLayout by bindView(R.id.drawer_layout)
    val toolbar: Toolbar by bindView(R.id.toolbar)
    val app_screen_container: AppScreenContainer by bindView(R.id.app_screen_container)


    override fun onEntered() {
        super.onEntered()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        setActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(mContext as Activity, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener {onNavigationItemSelected(it)}


        showScreen(app_screen_container, AppScreens.BrowserScreen, Website("facebook", "http://facebook.com"))

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                showScreen(app_screen_container, AppScreens.BrowserScreen, Website("facebook", "http://facebook.com"))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                showScreen(app_screen_container, AppScreens.BrowserScreen, Website("twitter", "http://twitter.com"))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                showScreen(app_screen_container, AppScreens.AboutScreen)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }



    override fun onBackPressed() : Boolean {
        return if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        } else {
            super.onBackPressed()
        }
    }

    fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_camera    -> {

            }
            R.id.nav_gallery   -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage    -> {

            }
            R.id.nav_share     -> {

            }
            R.id.nav_send      -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }



//    fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.main_menu, menu)
//        return true
//    }
//
//    fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        when (item.itemId) {
//            R.id.action_settings -> return true
//            else                 -> return super.onOptionsItemSelected(item)
//        }
//    }

}