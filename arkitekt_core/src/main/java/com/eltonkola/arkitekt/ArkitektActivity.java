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
            return getOrientation() != Configuration.ORIENTATION_LANDSCAPE;
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


    private int getOrientation(){
        return getResources().getConfiguration().orientation;
    }
    private class OrientationListener extends OrientationEventListener{

        private int mPreviousRotation = 0;

        public OrientationListener(Context context) { super(context); }

        @Override public void onOrientationChanged(int givenOrientation) {

            Logger.log(">>>>>>>>>>>>>>>> onOrientationChanged:" + givenOrientation);


            int orientation = givenOrientation;

            if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                orientation = 0;
            }

            orientation = orientation % 360;
            int newOrientation;
            if (orientation < (0 * 90) + 45) {
                newOrientation = 0;
            } else if (orientation < (1 * 90) + 45) {
                newOrientation = 90;
            } else if (orientation < (2 * 90) + 45) {
                newOrientation = 180;
            } else if (orientation < (3 * 90) + 45) {
                newOrientation = 270;
            } else {
                newOrientation = 0;
            }

            if(mPreviousRotation != newOrientation){
                mPreviousRotation = newOrientation;
                Logger.log(">>>>>>>>>>>>>>>> onOrientationChanged !!DoUpdate!! was:" + mPreviousRotation + " is:" + newOrientation);
                setContentView(mScreens.get(mScreens.size()-1).onEnter(ArkitektActivity.this, mScreenNavigation));
                mScreens.get(mScreens.size()-1)._onEntered();

            }
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

