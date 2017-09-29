package com.eltonkola.arkitekt;

/**
 * Created by elton on 9/29/17.
 */

public interface ScreenNavigation {

    void close();


    void goTo(final String path, final Object param);
}
