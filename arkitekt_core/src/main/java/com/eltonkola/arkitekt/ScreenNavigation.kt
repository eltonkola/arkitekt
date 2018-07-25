package com.eltonkola.arkitekt

import android.view.MenuInflater

interface ScreenNavigation {

    val menuInflater: MenuInflater

    val isInPortraitMode: Boolean

    fun close()

    fun goTo(path: String, param: Any?)

    fun goToAndClose(path: String, param: Any?)

    fun toastShort(msg: String)

    fun toastLong(msg: String)
}
