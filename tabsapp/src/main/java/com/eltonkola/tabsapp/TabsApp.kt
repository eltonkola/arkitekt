package com.eltonkola.tabsapp

import com.eltonkola.arkitekt.ArkitektApp
import com.eltonkola.config.AppRoute

class TabsApp : ArkitektApp() {

    override fun onCreate() {
        super.onCreate()

        routeConfig(AppRoute.routes)

    }

    companion object {

        fun getApp(): TabsApp {
            return app() as TabsApp
        }

    }


}
