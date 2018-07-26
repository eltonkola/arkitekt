package com.eltonkola.todoapp2.ui

import com.eltonkola.annotations.ScreenView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.webkit.WebView

import com.eltonkola.arkitekt.AppScreen
import com.eltonkola.todoapp2.R
import com.eltonkola.todoapp2.model.Todo

@ScreenView(path = "/view")
class DetailsScreen : AppScreen<Todo>() {

    override val view: Int
        get() = R.layout.details_screen

    override fun onEntered() {
        super.onEntered()

        val toolbar = mRootView!!.findViewById<Toolbar>(R.id.toolbar)
        toolbar.navigationIcon = mContext!!.resources.getDrawable(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { close() }
        toolbar.title = mScreenParam!!.title


        val webview = mRootView!!.findViewById<WebView>(R.id.webview)
        webview.loadData(mScreenParam!!.note, "text/html", null)

        Log.v("eltonkolas", mScreenParam!!.note)

    }
}