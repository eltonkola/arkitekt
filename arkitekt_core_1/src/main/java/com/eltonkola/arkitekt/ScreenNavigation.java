package com.eltonkola.arkitekt;

import android.view.MenuInflater;

/**
 * Created by elton on 9/29/17.
 */

public interface ScreenNavigation {

    void close();

    void goTo(final String path, final Object param);

    void goToAndClose(final String path, final Object param);

    MenuInflater getMenuInflater();

    void toastShort(String msg);

    void toastLong(String msg);

    boolean isInPortraitMode();
}
