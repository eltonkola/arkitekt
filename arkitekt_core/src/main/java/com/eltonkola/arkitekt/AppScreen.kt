package com.eltonkola.arkitekt

import android.content.Context
import android.content.Intent
import android.support.annotation.IdRes
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View

abstract class AppScreen<T> {

    private var mScreenNavigation: ScreenNavigation? = null

    abstract val view: Int

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


    fun goToAndClose(path: String) {
        Logger.log(">>>>>>>>>>>>>>>> AppScreen goToAndClose:$path")
        goToAndClose(path, null)
    }

    fun goToAndClose(path: String, param: Any?) {
        Logger.log(">>>>>>>>>>>>>>>> AppScreen goToAndClose:$path")
        if (mScreenNavigation == null) {
            return
        }
        mScreenNavigation!!.goToAndClose(path, param!!)
    }

    fun _onEntered() {
        Logger.log(">>>>>>>>>>>>>>>> AppScreen _onEntered")
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


}
