package com.eltonkola.todoapp2.ui

import android.support.v7.widget.Toolbar
import android.util.Log
import android.webkit.WebView
import com.eltonkola.annotations.ScreenView
import com.eltonkola.arkitekt.AppScreen
import com.eltonkola.todoapp2.R
import com.eltonkola.todoapp2.model.Todo
import kotterknife.bindView

@ScreenView(path = "/view")
class DetailsScreen : AppScreen<Todo>() {

    override val view: Int get() = R.layout.details_screen

    val toolbar: Toolbar by bindView(R.id.toolbar)
    val webview: WebView by bindView(R.id.webview)

    override fun onEntered() {
        super.onEntered()

        toolbar.navigationIcon = mContext!!.resources.getDrawable(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { close() }
        toolbar.title = mScreenParam!!.title


        webview.loadData(mScreenParam!!.note, "text/html", null)

        Log.v("eltonkolas", mScreenParam!!.note)

    }
}