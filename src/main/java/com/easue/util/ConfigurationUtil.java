package com.easue.util;

import com.easue.holder.ProxyBeanHolder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据工具类：标识注解，前置，后置，环绕
 */
public class ConfigurationUtil {
    /**
     * aop 标识注解类 = @AspectJ
     */
    public static final String AOP_POINT_ANNOTATION = "com.easue.annotation.AopJ";
    /**
     * 前置通知注解类
     */
    public static final String BEFORE = "com.easue.annotation.BeforeEasue";
    /**
     * 后置通知注解类
     */
    public static final String AFTER = "com.easue.annotation.AfterEasue";
    /**
     * 环绕通知注解类
     */
    public static final String AROUND = "com.easue.annotation.AroundEasue";
    /**
     * 存放需代理的全部目标对象类（保存带有@Repository的类）
     * 保存格式：
     *      {com.easue.dao.IndexDao=[com.easue.holder.ProxyBeanHolder@6166e06f, com.easue.holder.ProxyBeanHolder@49e202ad, com.easue.holder.ProxyBeanHolder@1c72da34]}
     */
    public static volatile Map<String, List<ProxyBeanHolder>> clazzProxyBeanHolder = new ConcurrentHashMap<>();
}
