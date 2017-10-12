package com.eltonkola.arkitekt;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.Toast;

import java.util.Stack;

/**
 * Created by elton on 9/28/17.
 */

public class ArkitektActivity extends AppCompatActivity {

    Stack<AppScreen> mScreens = new Stack<>();

    private ScreenNavigation mScreenNavigation = new ScreenNavigation() {
        @Override
        public void close() {
            closeScreen();
        }

        @Override
        public void goTo(final String path, final Object param) {
            loadScreen(path, param);
        }

        @Override
        public void goToAndClose(String path, Object param) {
            loadScreenAndClose(path, param);
        }

        @Override
        public MenuInflater getMenuInflater() {
            return ArkitektActivity.this.getMenuInflater();
        }

        @Override
        public void toastShort(String msg) {
            Toast.makeText(ArkitektActivity.this, msg, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void toastLong(String msg) {
            Toast.makeText(ArkitektActivity.this, msg, Toast.LENGTH_LONG).show();
        }

        @Override
        public boolean isInPortraitMode() {
            return getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE;
        }
    };

    private OrientationEventListener mOrientationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadScreen("/", null);
        mOrientationListener = new OrientationListener(ArkitektActivity.this);
        mOrientationListener.enable();
    }

    private class OrientationListener extends OrientationEventListener{
        final int ROTATION_O    = 1;
        final int ROTATION_90   = 2;
        final int ROTATION_180  = 3;
        final int ROTATION_270  = 4;

        private int rotation = 0;
        public OrientationListener(Context context) { super(context); }

        @Override public void onOrientationChanged(int orientation) {
            if( (orientation < 35 || orientation > 325) && rotation!= ROTATION_O){ // PORTRAIT
                rotation = ROTATION_O;
                Logger.log(">>>>>>>>>>>>>>>> ArkitektActivity rotation ROTATION_O");

            }
            else if( orientation > 145 && orientation < 215 && rotation!=ROTATION_180){ // REVERSE PORTRAIT
                rotation = ROTATION_180;
                Logger.log(">>>>>>>>>>>>>>>> ArkitektActivity rotation ROTATION_180");
            }
            else if(orientation > 55 && orientation < 125 && rotation!=ROTATION_270){ // REVERSE LANDSCAPE
                rotation = ROTATION_270;
                Logger.log(">>>>>>>>>>>>>>>> ArkitektActivity rotation ROTATION_270");
            }
            else if(orientation > 235 && orientation < 305 && rotation!=ROTATION_90){ //LANDSCAPE
                rotation = ROTATION_90;
                Logger.log(">>>>>>>>>>>>>>>> ArkitektActivity rotation ROTATION_90");
            }

            setContentView(mScreens.get(mScreens.size()-1).onEnter(ArkitektActivity.this, mScreenNavigation));
            mScreens.get(mScreens.size()-1)._onEntered();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOrientationListener.disable();
    }

    private void loadScreen(final String path, final Object param){
        AppScreen screen = ArkitektApp.app().getScreen(path, param);
        setContentView(screen.onEnter(this, mScreenNavigation));
        screen._onEntered();
        mScreens.add(screen);
    }

    private void loadScreenAndClose(final String path, final Object param){

        AppScreen lastScreen = mScreens.pop();
        lastScreen.onExit();

        AppScreen screen = ArkitektApp.app().getScreen(path, param);
        setContentView(screen.onEnter(this, mScreenNavigation));
        screen._onEntered();
        mScreens.add(screen);
    }

    private void closeScreen(){
        AppScreen screen = mScreens.pop();
        screen.onExit();

        if(mScreens.empty()){
            finish();
            return;
        }

        AppScreen previous = mScreens.pop();

        setContentView(previous.onEnter(this, mScreenNavigation));
        previous._onEntered();
        mScreens.add(previous);
    }

    @Override
    public void onBackPressed() {
        if(mScreens.size() > 1){
            closeScreen();
        }else{
            super.onBackPressed();
        }

    }

}

