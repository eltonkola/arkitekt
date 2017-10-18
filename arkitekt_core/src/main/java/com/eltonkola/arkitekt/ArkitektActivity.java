package com.eltonkola.arkitekt;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.OrientationEventListener;
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

        private boolean mPreviousIsPortrait = getOrientation() == Configuration.ORIENTATION_PORTRAIT;
        private int lastAngle = 0;
        public OrientationListener(Context context) { super(context); }

        @Override public void onOrientationChanged(int orientation) {

            AppScreen currentScreen = mScreens.get(mScreens.size()-1);

            int angle = getDirectionDegrees(orientation);

            //rerender view is required
            switch (currentScreen.getReloadOnOrientationChange()){
                case NONE:
                    return;
                case ALL:
                    forceRefreshScreen(currentScreen);
                    return;
                case TWO_DIRECTIONS:
                    boolean isPortrait = angle == 0 || angle == 180;
                    if(mPreviousIsPortrait !=  isPortrait) {
                        mPreviousIsPortrait = isPortrait;
                        Logger.log(">>>>>>>>>>>>>>>>onOrientationChanged TWO_DIRECTIONS !!DoUpdate!! was:" + mPreviousIsPortrait + " is:" + isPortrait);
                        forceRefreshScreen(currentScreen);
                    }
                    return;
                case FOUR_DIRECTIONS:
                    if(lastAngle != angle){
                        lastAngle = angle;
                        Logger.log(">>>>>>>>>>>>>>>>onOrientationChanged FOUR_DIRECTIONS !!DoUpdate!! was:" + lastAngle + " is:" + angle);
                        forceRefreshScreen(currentScreen);
                    }

                    return;
            }

            //send orientation event
            switch (currentScreen.getUpdateOrientationEvent()){
                case NONE:
                    return;
                case ALL:
                    currentScreen.onOrientationChange(mScreenNavigation.isInPortraitMode());
                    return;
                case TWO_DIRECTIONS:
                    boolean isPortrait = angle == 0 || angle == 180;
                    if(mPreviousIsPortrait !=  isPortrait) {
                        mPreviousIsPortrait = isPortrait;
                        Logger.log(">>>>>>>>>>>>>>>>onOrientationChanged TWO_DIRECTIONS !!DoUpdate!! was:" + mPreviousIsPortrait + " is:" + isPortrait);
                        currentScreen.onOrientationChange(mScreenNavigation.isInPortraitMode());
                    }
                    return;
                case FOUR_DIRECTIONS:
                    if(lastAngle != angle){
                        lastAngle = angle;
                        Logger.log(">>>>>>>>>>>>>>>>onOrientationChanged FOUR_DIRECTIONS !!DoUpdate!! was:" + lastAngle + " is:" + angle);
                        currentScreen.onOrientationChange(mScreenNavigation.isInPortraitMode());
                    }

                    return;
            }

        }
    }

    private void forceRefreshScreen(AppScreen appScreen){
        setContentView(appScreen.onEnter(ArkitektActivity.this, mScreenNavigation));
        appScreen._onEntered();
    }

    private int getDirectionDegrees(int givenOrientation){
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

        return  newOrientation;
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

    @Override
    protected void onPause() {
        if(mScreens.size() > 0) {
            AppScreen currentScreen = mScreens.get(mScreens.size() - 1);
            currentScreen.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mScreens.size() > 0) {
            AppScreen currentScreen = mScreens.get(mScreens.size() - 1);
            currentScreen.onResume();
        }
    }


}

