package com.easue.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 自定义 环绕通知注解类
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AroundEasue {
    String value() default "";
}
