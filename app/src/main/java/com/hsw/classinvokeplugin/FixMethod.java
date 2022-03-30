package com.hsw.classinvokeplugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: hsw
 * @date: 2022/3/29
 * @desc:
 */
@Target(value = {ElementType.TYPE,ElementType.METHOD})
public @interface FixMethod {
    String desc() default "";
}
