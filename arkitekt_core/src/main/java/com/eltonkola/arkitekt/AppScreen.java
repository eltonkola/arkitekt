package com.eltonkola.arkitekt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by elton on 9/28/17.
 */

public abstract class AppScreen<T> {

    private ScreenNavigation mScreenNavigation;

    public abstract int getView();

    protected Context mContext;
    protected View mRootView;

    private boolean firstTime = true;

    protected T mScreenParam;

    public void setParameter(T param){
        mScreenParam = param;
    }

    public View onEnter(final Context context, final ScreenNavigation screenNavigation) {

        if(mRootView!=null){
            return mRootView;
        }

        mContext = context;
        mScreenNavigation = screenNavigation;
        LayoutInflater mLayoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRootView = mLayoutInflater.inflate(getView(), null);


        return mRootView;
    }

    public void onEntered(){
        firstTime = false;
    }

    public void onExit() {

        mRootView = null;
        mContext = null;
    }

    public void close(){
        if(mScreenNavigation == null){
            return;
        }
        mScreenNavigation.close();
    }

    public void goTo(final String path, final Object param){
        if(mScreenNavigation == null){
            return;
        }
        mScreenNavigation.goTo(path, param);
    }

    public void _onEntered(){
        if(firstTime){
            onEntered();
        }
    }

}
