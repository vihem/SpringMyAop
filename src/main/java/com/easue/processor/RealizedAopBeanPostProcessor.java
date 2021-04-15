package com.easue.processor;

import com.easue.util.ConfigurationUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;

/**
 * ʵ��Aop��Bean���ô����� --> ɨ���@Component���ࡣ
 * aopʵ��BeanPostProcessorʵ�ֵ�postProcessBeforeInstantiation�������ж�bean�Ƿ���Set targetSourcedBeans���棬
 * ����ڣ���ȡ��targetSource��Ȼ��createProxy���������ǵĴ������������ǵĴ�����󷵻س�ȥ��
 */
public class RealizedAopBeanPostProcessor implements BeanPostProcessor {
    //ɨ�����еĴ�@Component��@Repository��bean������һ����com.easue.config.AppConfig$$EnhancerBySpringCGLIB$$7a87cbc4
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        String targetClass = bean.getClass().getName();
        System.out.println("|-->RealizedAopBeanPostProcessor.targetClass = " + targetClass);
        System.out.println("|-->    "+ConfigurationUtil.clazzProxyBeanHolder);
        Object object = bean;
        //clazzProxyBeanHolder ��һ��map��containsKey��map��һ���������жϸ�ӳ��(clazzProxyBeanHolder)����ָ������ӳ��(targetClass)����bean�Ƿ���Set targetSourcedBeans����
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
