package com.easue.processor;

import com.easue.util.ConfigurationUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;

public class RealizedAopBeanPostProcessor implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        String targetClass = bean.getClass().getName();
        Object object = bean;
        if (ConfigurationUtil.clazzProxyBeanHolder.containsKey(targetClass)){
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(object.getClass());
            enhancer.setCallback(new CustomizedProxyInterceptor(ConfigurationUtil.clazzProxyBeanHolder.get(targetClass)));
            object =  enhancer.create();
        }
        return object;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
