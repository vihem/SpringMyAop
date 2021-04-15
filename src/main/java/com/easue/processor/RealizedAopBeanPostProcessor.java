package com.easue.processor;

import com.easue.util.ConfigurationUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;

/**
 * 实现Aop的Bean后置处理器 --> 扫描带@Component的类。
 * aop实现BeanPostProcessor实现的postProcessBeforeInstantiation方法，判断bean是否在Set targetSourcedBeans里面，
 * 如果在，就取到targetSource，然后createProxy，创建我们的代理对象。最后将我们的代理对象返回出去。
 */
public class RealizedAopBeanPostProcessor implements BeanPostProcessor {
    //扫描所有的带@Component、@Repository的bean，还有一个：com.easue.config.AppConfig$$EnhancerBySpringCGLIB$$7a87cbc4
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        String targetClass = bean.getClass().getName();
        System.out.println("|-->RealizedAopBeanPostProcessor.targetClass = " + targetClass);
        System.out.println("|-->    "+ConfigurationUtil.clazzProxyBeanHolder);
        Object object = bean;
        //clazzProxyBeanHolder 是一个map，containsKey是map的一个方法：判断该映射(clazzProxyBeanHolder)包含指定键的映射(targetClass)，即bean是否在Set targetSourcedBeans里面
        if (ConfigurationUtil.clazzProxyBeanHolder.containsKey(targetClass)){//{com.easue.dao.IndexDao=[com.easue.holder.ProxyBeanHolder@71248c21, com.easue.holder.ProxyBeanHolder@442675e1, com.easue.holder.ProxyBeanHolder@6166e06f]}
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
