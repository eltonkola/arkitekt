package com.eltonkola.arkitekt.demo;

import com.eltonkola.arkitekt.ArkitektApp;

/**
 * Created by elton on 9/29/17.
 */

public class MainApp extends ArkitektApp {

    public static final String PATH_ROOT = "/";
    public static final String PATH_DETAILS = "/details";
    public static final String PATH_SELECT_IMAGE = "/select_image";

    @Override
    protected void routeConfig() {

        addScreen(PATH_ROOT, MainScreen.class);
        addScreen(PATH_DETAILS, DetailsScreen.class);
        addScreen(PATH_SELECT_IMAGE, SelectImageScreen.class);

    }


}
