package com.eltonkola.tabsapp.ui

import android.support.v7.widget.Toolbar
import android.webkit.WebView
import com.eltonkola.annotations.ScreenView
import com.eltonkola.arkitekt.AppScreen
import com.eltonkola.tabsapp.R
import kotterknife.bindView

@ScreenView(path = "/about")
class AboutScreen : AppScreen<Void>() {

    override val view: Int get() = R.layout.details_screen

    val toolbar: Toolbar by bindView(R.id.toolbar)
    val webview: WebView by bindView(R.id.webview)

    override fun onEntered() {
        super.onEntered()

        toolbar.navigationIcon = mContext!!.resources.getDrawable(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { close() }
        toolbar.title = "About"
        webview.loadData("V1.0", "text/html", null)
    }
}