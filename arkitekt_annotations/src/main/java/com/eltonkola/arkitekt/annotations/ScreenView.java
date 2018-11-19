package com.eltonkola.arkitekt.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) //class level
@Retention(RetentionPolicy.SOURCE) //we only need it at compile time
public @interface ScreenView {

    enum TYPE {
        FULL_VIEW, SUB_VIEW
    }

    String path() default "/";

    TYPE viewtype() default TYPE.FULL_VIEW;

}
