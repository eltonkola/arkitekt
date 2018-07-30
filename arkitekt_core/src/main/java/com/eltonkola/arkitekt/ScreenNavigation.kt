package com.eltonkola.arkitekt

import android.support.v7.widget.Toolbar
import android.view.MenuInflater
import com.eltonkola.arkitekt.views.AppScreenContainer

interface ScreenNavigation {

    val menuInflater: MenuInflater

    val isInPortraitMode: Boolean

    fun close()

    fun goTo(path: String, param: Any?)

    fun goToAndClose(path: String, param: Any?)

    fun toastShort(msg: String)

    fun toastLong(msg: String)

    fun showScreen(parent: AppScreen<*>, app_screen_container: AppScreenContainer, appScreenPath: String, param: Any?)

    fun setActionBar(toolbar: Toolbar)
}
