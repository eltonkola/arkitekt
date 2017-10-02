package com.eltonkola.arkitekt;

import android.view.MenuInflater;

/**
 * Created by elton on 9/29/17.
 */

public interface ScreenNavigation {

    void close();

    void goTo(final String path, final Object param);

    MenuInflater getMenuInflater();

}
