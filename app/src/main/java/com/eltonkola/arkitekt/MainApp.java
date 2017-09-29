package com.eltonkola.arkitekt;

/**
 * Created by elton on 9/29/17.
 */

public class MainApp extends ArkitektApp {

    public static final String PATH_ROOT = "/";
    public static final String PATH_DETAILS = "/details";

    @Override
    protected void routeConfig() {

        mAppScreens.put(PATH_ROOT, MainScreen.class);
        mAppScreens.put(PATH_DETAILS, DetailsScreen.class);

    }


}
