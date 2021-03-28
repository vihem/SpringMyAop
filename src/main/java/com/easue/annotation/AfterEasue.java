package com.easue.annotation;

/**
 * 自定义 后置通知注解类
 */
public @interface AfterEasue {
    /**
     * 表示你的注解有一个名为 value 的可选参数。不设置的话默认为“”。
     * 如果没有后面的 default ""，则表示这是一个必须的参数。不指定的话会报错。
     */
    String value() default "";
}
