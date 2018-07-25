package com.eltonkola.arkitekt

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuInflater
import android.view.OrientationEventListener
import android.widget.Toast

import java.util.Stack


class ArkitektActivity : AppCompatActivity() {

    internal var mScreens = Stack<AppScreen<*>>()

    private val mScreenNavigation = object : ScreenNavigation {

        override val menuInflater: MenuInflater
            get() = this@ArkitektActivity.menuInflater

        override val isInPortraitMode: Boolean
            get() = orientation != Configuration.ORIENTATION_LANDSCAPE

        override fun close() {
            closeScreen()
        }

        override fun goTo(path: String, param: Any?) {
            loadScreen(path, param)
        }

        override fun goToAndClose(path: String, param: Any?) {
            loadScreenAndClose(path, param)
        }

        override fun toastShort(msg: String) {
            Toast.makeText(this@ArkitektActivity, msg, Toast.LENGTH_SHORT).show()
        }

        override fun toastLong(msg: String) {
            Toast.makeText(this@ArkitektActivity, msg, Toast.LENGTH_LONG).show()
        }
    }

    private var mOrientationListener: OrientationEventListener? = null


    private val orientation: Int
        get() = resources.configuration.orientation


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadScreen("/", null)
        mOrientationListener = OrientationListener(this@ArkitektActivity)
        mOrientationListener!!.enable()
    }

    private inner class OrientationListener(context: Context) : OrientationEventListener(context) {

        private var mPreviousIsPortrait = orientation == Configuration.ORIENTATION_PORTRAIT
        private var lastAngle = 0

        override fun onOrientationChanged(orientation: Int) {

            val currentScreen = mScreens[mScreens.size - 1]

            val angle = getDirectionDegrees(orientation)

            //rerender view is required
            when (currentScreen.reloadOnOrientationChange) {
                AppScreen.ReloadOnOrientationChange.NONE            -> return
                AppScreen.ReloadOnOrientationChange.ALL             -> {
                    forceRefreshScreen(currentScreen)
                    return
                }
                AppScreen.ReloadOnOrientationChange.TWO_DIRECTIONS  -> {
                    val isPortrait = angle == 0 || angle == 180
                    if (mPreviousIsPortrait != isPortrait) {
                        mPreviousIsPortrait = isPortrait
                        Logger.log(">>>>>>>>>>>>>>>>onOrientationChanged TWO_DIRECTIONS !!DoUpdate!! was:$mPreviousIsPortrait is:$isPortrait")
                        forceRefreshScreen(currentScreen)
                    }
                    return
                }
                AppScreen.ReloadOnOrientationChange.FOUR_DIRECTIONS -> {
                    if (lastAngle != angle) {
                        lastAngle = angle
                        Logger.log(">>>>>>>>>>>>>>>>onOrientationChanged FOUR_DIRECTIONS !!DoUpdate!! was:$lastAngle is:$angle")
                        forceRefreshScreen(currentScreen)
                    }

                    return
                }
            }

            //send orientation event
            when (currentScreen.updateOrientationEvent) {
                AppScreen.UpdateOrientationEvent.NONE            -> return
                AppScreen.UpdateOrientationEvent.ALL             -> {
                    currentScreen.onOrientationChange(mScreenNavigation.isInPortraitMode)
                    return
                }
                AppScreen.UpdateOrientationEvent.TWO_DIRECTIONS  -> {
                    val isPortrait = angle == 0 || angle == 180
                    if (mPreviousIsPortrait != isPortrait) {
                        mPreviousIsPortrait = isPortrait
                        Logger.log(">>>>>>>>>>>>>>>>onOrientationChanged TWO_DIRECTIONS !!DoUpdate!! was:$mPreviousIsPortrait is:$isPortrait")
                        currentScreen.onOrientationChange(mScreenNavigation.isInPortraitMode)
                    }
                    return
                }
                AppScreen.UpdateOrientationEvent.FOUR_DIRECTIONS -> {
                    if (lastAngle != angle) {
                        lastAngle = angle
                        Logger.log(">>>>>>>>>>>>>>>>onOrientationChanged FOUR_DIRECTIONS !!DoUpdate!! was:$lastAngle is:$angle")
                        currentScreen.onOrientationChange(mScreenNavigation.isInPortraitMode)
                    }

                    return
                }
            }

        }
    }

    private fun forceRefreshScreen(appScreen: AppScreen<*>) {
        setContentView(appScreen.onEnter(this@ArkitektActivity, mScreenNavigation))
        appScreen._onEntered()
    }

    private fun getDirectionDegrees(givenOrientation: Int): Int {
        var orientation = givenOrientation

        if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
            orientation = 0
        }

        orientation = orientation % 360
        val newOrientation: Int
        if (orientation < 0 * 90 + 45) {
            newOrientation = 0
        } else if (orientation < 1 * 90 + 45) {
            newOrientation = 90
        } else if (orientation < 2 * 90 + 45) {
            newOrientation = 180
        } else if (orientation < 3 * 90 + 45) {
            newOrientation = 270
        } else {
            newOrientation = 0
        }

        return newOrientation
    }

    override fun onDestroy() {
        super.onDestroy()
        mOrientationListener!!.disable()
    }

    private fun loadScreen(path: String, param: Any?) {
        val screen = ArkitektApp.app().getScreen(path, param)
        setContentView(screen.onEnter(this, mScreenNavigation))
        screen._onEntered()
        mScreens.add(screen)
    }

    private fun loadScreenAndClose(path: String, param: Any?) {

        val lastScreen = mScreens.pop()
        lastScreen.onExit()

        val screen = ArkitektApp.app().getScreen(path, param)
        setContentView(screen.onEnter(this, mScreenNavigation))
        screen._onEntered()
        mScreens.add(screen)
    }

    private fun closeScreen() {
        val screen = mScreens.pop()
        screen.onExit()

        if (mScreens.empty()) {
            finish()
            return
        }

        val previous = mScreens.pop()

        setContentView(previous.onEnter(this, mScreenNavigation))
        previous._onEntered()
        mScreens.add(previous)
    }

    override fun onBackPressed() {
        if (mScreens.size > 1) {
            closeScreen()
        } else {
            super.onBackPressed()
        }

    }

    override fun onPause() {
        if (mScreens.size > 0) {
            val currentScreen = mScreens[mScreens.size - 1]
            currentScreen.onPause()
        }
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        if (mScreens.size > 0) {
            val currentScreen = mScreens[mScreens.size - 1]
            currentScreen.onResume()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (mScreens.size > 0) {
            val currentScreen = mScreens[mScreens.size - 1]
            currentScreen.onActivityResult(requestCode, resultCode, data)
        }
    }
}

