package com.easue.annotation;

import com.easue.selector.CustomizedAopImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * aop开关注解
 *  = @EnableAspectJAutoProxy
 *  = beans.xml 的 <aop:aspectj-autoproxy />
 */
@Retention(RetentionPolicy.RUNTIME)
@Import(CustomizedAopImportSelector.class)
public @interface EnableAop {
}
