package com.eltonkola.todoapp2.ui

import android.support.v7.widget.Toolbar
import android.webkit.WebView
import com.eltonkola.arkitekt.AppScreen
import com.eltonkola.tabsapp.R
import com.eltonkola.tabsapp.model.Website
import kotterknife.bindView
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.annotation.TargetApi
import android.widget.Toast
import android.webkit.WebViewClient
import com.eltonkola.arkitekt.annotations.ScreenView


@ScreenView(path = "/webview")
class BrowserScreen : AppScreen<Website>() {

    override val view: Int get() = R.layout.details_screen

    val toolbar: Toolbar by bindView(R.id.toolbar)
    val webview: WebView by bindView(R.id.webview)

    override fun onEntered() {
        super.onEntered()

        toolbar.navigationIcon = mContext!!.resources.getDrawable(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { close() }
        toolbar.title = mScreenParam!!.title

        webview.getSettings().setJavaScriptEnabled(true)
        webview.webViewClient = object : WebViewClient() {
            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                Toast.makeText(mContext, description, Toast.LENGTH_SHORT).show()
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            override fun onReceivedError(view: WebView, req: WebResourceRequest, rerr: WebResourceError) {
                onReceivedError(view, rerr.errorCode, rerr.description.toString(), req.url.toString())
            }
        }

        webview.loadUrl(mScreenParam!!.url)
    }
}