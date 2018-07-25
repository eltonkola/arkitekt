package com.eltonkola.todoapp2.ui;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.eltonkola.annotations.ScreenView;
import com.eltonkola.arkitekt.AppScreen;
import com.eltonkola.todoapp2.R;
import com.eltonkola.todoapp2.model.Todo;

@ScreenView(path = "/about")
public class AboutScreen extends AppScreen<Todo> {

    @Override
    public int getView() {
        return R.layout.details_screen;
    }

    @Override
    public void onEntered() {
        super.onEntered();

        final Toolbar toolbar =  getMRootView().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getMContext().getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
        toolbar.setTitle(getMScreenParam().getTitle());


        WebView webview = getMRootView().findViewById(R.id.webview);
        webview.loadData(getMScreenParam().getNote(), "text/html", null);

        Log.v("eltonkolas", getMScreenParam().getNote());

    }
}