package com.easue.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 自定义 后置通知注解类
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AfterEasue {
    /**
     * 表示你的注解有一个名为 value 的可选参数。不设置的话默认为“”。
     * 如果没有后面的 default ""，则表示这是一个必须的参数。不指定的话会报错。
     */
    String value() default "";
}
