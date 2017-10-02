package com.eltonkola.todoapp.ui;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.eltonkola.arkitekt.AppScreen;
import com.eltonkola.todoapp.R;
import com.eltonkola.todoapp.model.Todo;

/**
 * Created by elton on 9/29/17.
 */

public class DetailsScreen extends  AppScreen<Todo> {

    @Override
    public int getView() {
        return R.layout.details_screen;
    }

    @Override
    public void onEntered() {
        super.onEntered();

        final Toolbar toolbar =  mRootView.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(mContext.getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
        toolbar.setTitle(mScreenParam.getTitle());


        WebView webview = mRootView.findViewById(R.id.webview);
        webview.loadData(mScreenParam.getNote(), "text/html", null);

        Log.v("eltonkolas", mScreenParam.getNote());

    }
}
