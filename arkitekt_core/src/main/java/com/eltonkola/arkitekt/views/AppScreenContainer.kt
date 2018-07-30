package com.eltonkola.arkitekt.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.eltonkola.arkitekt.ArkitektActivity
import com.eltonkola.arkitekt.ArkitektApp
import com.eltonkola.arkitekt.R

class AppScreenContainer : FrameLayout {

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
//        val a = context.obtainStyledAttributes(attrs, R.styleable.AppScreenContainer, defStyle, 0)
//        a.recycle()

        setBackgroundColor(Color.RED)
    }

//    fun showScreen(appScreenPath: String, extra: Any? = null) {
//        //TODO - this does not belong here
//        val screen = ArkitektApp.app().getScreen(appScreenPath, extra)
//        removeAllViews()
//        val activity = context as ArkitektActivity
//        addView(screen.onEnter(activity, activity.mScreenNavigation))
//        invalidate()
//    }

}
