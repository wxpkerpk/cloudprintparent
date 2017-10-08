package com.wx.cloudprint.webserver.anotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Acess {
    String[] value() default {};

    String[] authorities() default {};

    String[] roles() default {};
}
