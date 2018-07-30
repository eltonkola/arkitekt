package com.eltonkola.arkitekt

import android.app.Application
import android.util.Log

import java.util.HashMap

open class ArkitektApp : Application() {

    protected var mAppScreens = HashMap<String, Class<*>>()

    override fun onCreate() {
        super.onCreate()
        mApp = this

/*
        //in memory annotation processing

        val obj = SecondaryScreen::class.java

        // Process @TesterInfo
        if (obj!!.isAnnotationPresent(ViewScreen::class.java)) {

            val annotation = obj!!.getAnnotation(ViewScreen::class.java!!)
            val testerInfo = annotation as ViewScreen

            Log.v("eltonkolaxx", "Priority : " + testerInfo.priority())
            Log.v("eltonkolaxx", "CreatedBy : " + testerInfo.createdBy())
            Log.v("eltonkolaxx", "Tags :")

            var tagLength = testerInfo.tags().size
            for (tag in testerInfo.tags()) {
                if (tagLength > 1) {
                    Log.v("eltonkolaxx", tag + ", ")
                } else {
                    Log.v("eltonkolaxx", tag)
                }
                tagLength--
            }

            Log.v("eltonkolaxx", "%nLastModified :%s%n%n" + testerInfo.lastModified())
        }
*/
    }

    fun routeConfig(config: Any){
        mAppScreens.clear()
        mAppScreens.putAll(config as HashMap<String, Class<*>>)
    }

    fun getScreen(route: String, param: Any?): AppScreen<*> {

        try {
            val screenClass = mAppScreens[route]
            val screen = screenClass!!.newInstance() as AppScreen<Any?>
            if (param != null) {
              screen.setParameter(param)
            }
            return screen
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ErrorScreen()
    }


    protected fun addScreen(path: String, type: Class<*>) {
        mAppScreens[path] = type
    }

    companion object {

        protected lateinit var mApp: ArkitektApp

        fun app(): ArkitektApp {
            return mApp
        }
    }
}

