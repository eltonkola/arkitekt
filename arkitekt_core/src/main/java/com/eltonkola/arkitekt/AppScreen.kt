package com.eltonkola.arkitekt

import android.content.Context
import android.content.Intent
import android.support.annotation.IdRes
import android.support.transition.Slide
import android.support.transition.Transition
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import com.eltonkola.arkitekt.views.AppScreenContainer
import java.util.HashMap

abstract class AppScreen<T> {

    private var mScreenNavigation: ScreenNavigation? = null

    abstract val view: Int

    open val menu: Int? = null

    open fun animationIn() : Transition {
        var t: Transition = Slide(Gravity.RIGHT)
        t.duration = 100
        return t
    }

    open fun animationOut() : Transition {
        var t: Transition = Slide(Gravity.RIGHT)
        t.duration = 100
        return t
    }



    val reloadOnOrientationChange: ReloadOnOrientationChange
        get() = ReloadOnOrientationChange.NONE
    val updateOrientationEvent: UpdateOrientationEvent
        get() = UpdateOrientationEvent.TWO_DIRECTIONS


    protected var mContext: Context? = null
    protected var mRootView: View? = null

    protected var mScreenParam: T? = null

    val isInPortraitMode: Boolean
        get() {
            Logger.log(">>>>>>>>>>>>>>>> AppScreen isInPortraitMode")
            return if (mScreenNavigation == null) {
                true
            } else mScreenNavigation!!.isInPortraitMode
        }

    protected val menuInflater: MenuInflater
        get() = mScreenNavigation!!.menuInflater

    fun onResume() {}

    fun onPause() {}

    enum class ReloadOnOrientationChange {
        NONE, TWO_DIRECTIONS, FOUR_DIRECTIONS, ALL
    }

    enum class UpdateOrientationEvent {
        NONE, TWO_DIRECTIONS, FOUR_DIRECTIONS, ALL
    }

    fun setParameter(param: T) {
        mScreenParam = param
    }

    fun onEnter(context: ArkitektActivity, screenNavigation: ScreenNavigation): View? {
        Logger.log(">>>>>>>>>>>>>>>> AppScreen onEnter " + this.javaClass.name)
        //        if(mRootView!=null){
        //            return mRootView;
        //        }

        mContext = context
        mScreenNavigation = screenNavigation
        val mLayoutInflater = mContext!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mRootView = mLayoutInflater.inflate(view, null)


        return mRootView
    }

    open fun onEntered() {
        Logger.log(">>>>>>>>>>>>>>>> AppScreen onEntered")
    }

    open fun onExit() {
        Logger.log(">>>>>>>>>>>>>>>> AppScreen onExit")
        mRootView = null
        mContext = null
    }

    fun close() {
        Logger.log(">>>>>>>>>>>>>>>> AppScreen close")
        if (mScreenNavigation == null) {
            return
        }

        closeAllSubScreens()

        mScreenNavigation!!.close()
    }


    fun goTo(path: String, param: Any?) {
        Logger.log(">>>>>>>>>>>>>>>> AppScreen goTo:$path - param:$param")
        if (mScreenNavigation == null) {
            return
        }
        mScreenNavigation!!.goTo(path, param)
    }

    fun goTo(path: String) {
        Logger.log(">>>>>>>>>>>>>>>> AppScreen goTo:$path")
        goTo(path, null)
    }

    fun goToAndClose(path: String, param: Any? = null) {
        Logger.log(">>>>>>>>>>>>>>>> AppScreen goToAndClose:$path")
        if (mScreenNavigation == null) {
            return
        }
        closeAllSubScreens()
        mScreenNavigation!!.goToAndClose(path, param!!)
    }

    fun _onEntered() {
        Logger.log(">>>>>>>>>>>>>>>> AppScreen _onEntered")
        subScreens.forEach { container, appScreen ->
            appScreen._onEntered()
        }
        onEntered()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        Logger.log(">>>>>>>>>>>>>>>> AppScreen _onActivityResult")
    }

    fun toast(msg: String) {
        mScreenNavigation!!.toastShort(msg)
    }

    fun toastLong(msg: String) {
        mScreenNavigation!!.toastLong(msg)
    }

    fun onOrientationChange(inPortraitMode: Boolean) {

    }


    fun startActivityForResult(intent: Intent, requestCode: Int) {
        (mContext as ArkitektActivity).startActivityForResult(intent, requestCode)
    }

    fun findViewById(@IdRes id: Int): View {
        return mRootView!!.findViewById(id)
    }

    /**
     * do we want to handle the event?
     * default value is false, so the activity will handle this
     */
    open fun onBackPressed(): Boolean {
        return false
    }



    fun showScreen(app_screen_container: AppScreenContainer, appScreenPath: String, param: Any? = null) {
        Logger.log(">>>>>>>>>>>>>>>> AppScreen showScreen:$appScreenPath app_screen_container:$app_screen_container - param:$param")
        if (mScreenNavigation == null) {
            return
        }
        mScreenNavigation!!.showScreen(this, app_screen_container, appScreenPath, param)
    }


    /*
    We can have one or more screen container on a screen, lets update here the last active subscreen
    */
    protected var subScreens = HashMap<AppScreenContainer, AppScreen<*>>()

    fun currentSubscreen(container: AppScreenContainer, subscreen: AppScreen<*>) {
        subScreens.put(container, subscreen)
    }

    private fun closeAllSubScreens() {
        subScreens.forEach { container, appScreen ->
            appScreen.onPause()
            container.removeAllViews()
        }
        subScreens.clear()
    }

    fun setActionBar(toolbar: Toolbar){
        Logger.log(">>>>>>>>>>>>>>>> AppScreen setActionBar:$toolbar")
        if (mScreenNavigation == null) {
            return
        }
        mScreenNavigation!!.setActionBar(toolbar)

    }


}
