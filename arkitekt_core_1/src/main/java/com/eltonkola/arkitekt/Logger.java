package com.eltonkola.arkitekt;

import android.util.Log;

/**
 * Created by elton on 10/12/17.
 */

public class Logger {

    public static void log(final String log){
        if(BuildConfig.DEBUG){
            Log.v("eltonkolazx", "" + log);
        }
    }
}
