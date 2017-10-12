package com.eltonkola.arkitekt.demo;

import android.view.View;
import android.widget.TextView;

import com.eltonkola.arkitekt.AppScreen;

/**
 * Created by elton on 9/29/17.
 */

public class MainScreen extends AppScreen<Void> {

    @Override
    public int getView() {
        return R.layout.main_screen;
    }

    private int sa = 0;

    @Override
    public void onEntered() {
        super.onEntered();

        final TextView mainText =  mRootView.findViewById(R.id.mainText);
        mainText.setText("This is a dynamic screen");

        mRootView.findViewById(R.id.butKlik).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sa++;
                mainText.setText("You clicked " + sa + " times!");
            }
        });

        mRootView.findViewById(R.id.butClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
            }
        });

        mRootView.findViewById(R.id.butDetails1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goTo(MainApp.PATH_DETAILS, "1");
            }
        });

        mRootView.findViewById(R.id.butDetails2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goTo(MainApp.PATH_DETAILS, "2");
            }
        });

        mRootView.findViewById(R.id.butDetails3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goTo(MainApp.PATH_DETAILS, "3");
            }
        });


        mRootView.findViewById(R.id.butOpenAndClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAndClose(MainApp.PATH_DETAILS, "100");
            }
        });

        mRootView.findViewById(R.id.butCloseAndOpen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAndClose(MainApp.PATH_DETAILS, "200");
            }
        });

    }

    @Override
    public void onExit() {
        super.onExit();
    }

}
