package com.eltonkola.arkitekt;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;

/**
 * Created by elton on 9/28/17.
 */

public abstract class AppScreen<T> {

    private ScreenNavigation mScreenNavigation;

    public abstract int getView();

    protected Context mContext;
    protected View mRootView;

    protected T mScreenParam;

    public void setParameter(T param){
        mScreenParam = param;
    }

    public View onEnter(final Context context, final ScreenNavigation screenNavigation) {
        Logger.log(">>>>>>>>>>>>>>>> AppScreen onEnter " + this.getClass().getName() );
//        if(mRootView!=null){
//            return mRootView;
//        }

        mContext = context;
        mScreenNavigation = screenNavigation;
        LayoutInflater mLayoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        Logger.log(">>> original id:" + getView());
//
//        final String layoutName = mContext.getResources().getResourceEntryName(getView());
//        Logger.log(">>> name:" + layoutName);
//
//        int resId = mContext.getResources().getIdentifier(layoutName,  "layout", context.getPackageName());
//
//        Logger.log(">>> resId:" + resId);

        mRootView = mLayoutInflater.inflate(getView(), null);


        return mRootView;
    }

    public void onEntered(){
        Logger.log(">>>>>>>>>>>>>>>> AppScreen onEntered");
    }

    public void onExit() {
        Logger.log(">>>>>>>>>>>>>>>> AppScreen onExit");
        mRootView = null;
        mContext = null;
    }

    public void close(){
        Logger.log(">>>>>>>>>>>>>>>> AppScreen close");
        if(mScreenNavigation == null){
            return;
        }
        mScreenNavigation.close();
    }

    public void goTo(final String path, final Object param){
        Logger.log(">>>>>>>>>>>>>>>> AppScreen goTo:" + path + " - param:" + param);
        if(mScreenNavigation == null){
            return;
        }
        mScreenNavigation.goTo(path, param);
    }

    public void goTo(final String path){
        Logger.log(">>>>>>>>>>>>>>>> AppScreen goTo:" + path);
        goTo(path, null);
    }


    public void goToAndClose(final String path){
        Logger.log(">>>>>>>>>>>>>>>> AppScreen goToAndClose:" + path);
        goToAndClose(path, null);
    }

    public void goToAndClose(final String path, final Object param){
        Logger.log(">>>>>>>>>>>>>>>> AppScreen goToAndClose:" + path);
        if(mScreenNavigation == null){
            return;
        }
        mScreenNavigation.goToAndClose(path, param);
    }

    public boolean isInPortraitMode(){
        Logger.log(">>>>>>>>>>>>>>>> AppScreen isInPortraitMode");
        if(mScreenNavigation == null){
            return true;
        }
        return mScreenNavigation.isInPortraitMode();
    }

    public void _onEntered(){
        Logger.log(">>>>>>>>>>>>>>>> AppScreen _onEntered");
        onEntered();
    }

    public void toast(final String msg){
        mScreenNavigation.toastShort(msg);
    }

    public void toastLong(final String msg){
        mScreenNavigation.toastLong(msg);
    }

    protected MenuInflater getMenuInflater(){
        return mScreenNavigation.getMenuInflater();
    }

}
