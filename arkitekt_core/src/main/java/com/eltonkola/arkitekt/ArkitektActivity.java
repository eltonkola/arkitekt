package com.eltonkola.arkitekt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadScreen("/", null);

    }

    private void loadScreen(final String path, final Object param){
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

}

