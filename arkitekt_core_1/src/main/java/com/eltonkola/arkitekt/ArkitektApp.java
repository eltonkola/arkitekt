package com.eltonkola.arkitekt;

import android.app.Application;

import java.util.HashMap;

/**
 * Created by elton on 9/28/17.
 */

public abstract class ArkitektApp extends Application {

    protected static ArkitektApp mApp;

    public static ArkitektApp app(){
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        routeConfig();
    }

    protected abstract void routeConfig();

    protected HashMap<String, Class> mAppScreens = new HashMap<>();

    public AppScreen getScreen(final String route, final Object param){

        try {
            Class<?> screenClass = mAppScreens.get(route);
            //Constructor<?> ctor = screenClass.getConstructor();
            //AppScreen screen = ctor.newInstance(new Object[]{ctorArgument});
            AppScreen screen = (AppScreen)screenClass.newInstance();
            if(param!=null) {
                screen.setParameter(param);
            }
            return screen;
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ErrorScreen();
    }


    protected void addScreen(String path, Class type){
        mAppScreens.put(path, type);
    }
}

