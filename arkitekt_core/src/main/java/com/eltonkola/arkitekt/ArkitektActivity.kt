package com.eltonkola.arkitekt

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.transition.Transition
import android.support.transition.TransitionManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.OrientationEventListener
import android.widget.FrameLayout
import android.widget.Toast
import com.eltonkola.arkitekt.views.AppScreenContainer
import java.util.*


class ArkitektActivity : AppCompatActivity() {

    internal var mScreens = Stack<AppScreen<*>>()

    val mScreenNavigation = object : ScreenNavigation {
        override fun setActionBar(toolbar: Toolbar) {
            setSupportActionBar(toolbar)
        }

        override fun showScreen(parent: AppScreen<*>, app_screen_container: AppScreenContainer, appScreenPath: String, param: Any?) {
            loadSubScreen(parent, app_screen_container, appScreenPath, param)
        }

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


    lateinit var arkitekt_container : FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.arkitekt_activity)
        arkitekt_container = findViewById(R.id.arkitekt_container)

        loadScreen("/", null)
        mOrientationListener = OrientationListener(this@ArkitektActivity)
        mOrientationListener!!.enable()
    }

    private inner class OrientationListener(context: Context) : OrientationEventListener(context) {

        private var mPreviousIsPortrait = orientation == Configuration.ORIENTATION_PORTRAIT
        private var lastAngle = 0

        override fun onOrientationChanged(orientation: Int) {

            

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


    /*
    * For current screen, add a subscreen on that container
    */
    private fun loadSubScreen(parent: AppScreen<*>, container: AppScreenContainer, path: String, param: Any?) {

        val subscreen = ArkitektApp.app().getScreen(path, param)

        val view = subscreen.onEnter(this, mScreenNavigation)
        view!!.setBackgroundColor(Color.WHITE)

        container.removeAllViews()
        container.addView(view)
        subscreen._onEntered()

        parent.currentSubscreen(container, subscreen)

    }





    private fun loadScreen(path: String, param: Any?) {
        val screen = ArkitektApp.app().getScreen(path, param)

        //setContentView(screen.onEnter(this, mScreenNavigation))

        addView(screen, Runnable {

            screen._onEntered()
            mScreens.add(screen)
        })

    }

    private fun loadScreenAndClose(path: String, param: Any?) {

        val lastScreen = mScreens.pop()
        removeView(lastScreen, Runnable {
            lastScreen.onExit()

            val screen = ArkitektApp.app().getScreen(path, param)

            addView(screen, Runnable {
                //setContentView(screen.onEnter(this, mScreenNavigation))
                screen._onEntered()
                mScreens.add(screen)
            })

        })



    }

    private fun closeScreen() {

        Logger.log(">>>Close screen!")


        val screen = mScreens.pop()
        Logger.log(">>>screen to remove:" + screen.javaClass.name)
        screen.onExit()

        removeView(screen, Runnable {


            if (mScreens.empty()) {
                finish()
                return@Runnable
            }

            val previous = mScreens.peek()
            Logger.log(">>>previous is :" + previous.javaClass.name)
            //setContentView(previous.onEnter(this, mScreenNavigation))

            //addView(previous.onEnter(this, mScreenNavigation))
//        previous.onEntered()

            Logger.log(">>>mScreens  :" + mScreens.size)

            previous._onEntered()


            invalidateOptionsMenu()
        })


    }

    fun addView(screen: AppScreen<*>, onDone: Runnable){


            val view = screen.onEnter(this, mScreenNavigation)

             view!!.setBackgroundColor(Color.WHITE)


        TransitionManager.beginDelayedTransition(arkitekt_container, screen.animationIn())

        arkitekt_container.addView(view)

        onDone.run()

        invalidateOptionsMenu()

    }

    fun removeView(screen: AppScreen<*>, onDone: Runnable){

        val viewToRemove = arkitekt_container.getChildAt(arkitekt_container.childCount - 1)
        TransitionManager.beginDelayedTransition(arkitekt_container, screen.animationOut())

        arkitekt_container.removeView(viewToRemove)


        //TransitionManager.endTransitions(arkitekt_container)


        onDone.run()

        invalidateOptionsMenu()

    }




    override fun onBackPressed() {

        if(currentScreen.onBackPressed()){
            return
        }else {
            if (mScreens.size > 1) {
                closeScreen()
            } else {
                super.onBackPressed()
            }
        }

    }

    override fun onPause() {
        if (mScreens.size > 0) {
            currentScreen.onPause()
        }
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        if (mScreens.size > 0) {
            currentScreen.onResume()
        }
    }

    val currentScreen : AppScreen<*> get() = mScreens[mScreens.size - 1]

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (mScreens.size > 0) {
            currentScreen.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        if(currentScreen.menu !=null ){
            getMenuInflater().inflate(currentScreen.menu!!, menu)
            return true
        }

        return super.onCreateOptionsMenu(menu)
    }
}

