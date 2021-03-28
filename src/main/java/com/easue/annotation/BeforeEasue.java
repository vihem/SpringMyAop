package com.easue.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 自定义 前置通知注解类
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface BeforeEasue {
    String value() default "";
}
