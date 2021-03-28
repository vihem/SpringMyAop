package com.easue.processor;

import com.easue.holder.ProxyBeanHolder;
import com.easue.util.ConfigurationUtil;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理对象
 *  -- 定制代理拦截器
 */
public class CustomizedProxyInterceptor implements MethodInterceptor {

    // 用于接收切面信息
    List<ProxyBeanHolder> proxyBeanHolders;
    public CustomizedProxyInterceptor(List<ProxyBeanHolder> proxyBeanHolders) {
        this.proxyBeanHolders = proxyBeanHolders;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        // 处理 前置 及 环绕前置 通知
        for (ProxyBeanHolder proxyBeanHolder: proxyBeanHolders) {
            String annotationName = proxyBeanHolder.getAnnotationName(); //获取注解名
            if (annotationName.equals(ConfigurationUtil.BEFORE) || annotationName.equals(ConfigurationUtil.AROUND)) {
                this.doProxy(proxyBeanHolder);
            }
        }
        Object result = null;
        try {
            result = methodProxy.invokeSuper(o, objects);
        } catch (Exception e) {
            System.out.println("get ex:"+e.getMessage());
            throw e;
        }
        //处理 后置 及 环绕前置 通知
        for (ProxyBeanHolder proxyBeanHolder: proxyBeanHolders) {
            String annotationName = proxyBeanHolder.getAnnotationName();
            if (annotationName.equals(ConfigurationUtil.AFTER)||annotationName.equals(ConfigurationUtil.AROUND))
                this.doProxy(proxyBeanHolder);
        }
        return result;
    }

    /**
     * 处理代理操作
     * @param proxyBeanHolder
     */
    private void doProxy(ProxyBeanHolder proxyBeanHolder){
        String className = proxyBeanHolder.getClassName();
        String methodName = proxyBeanHolder.getMethodName();
        Object clazz = null;
        try {
            clazz = Class.forName(className);
            Method[] methods = ((Class) clazz).getMethods();
            for (Method poxyMethod:methods)
                if (poxyMethod.getName().equals(methodName))
                    poxyMethod.invoke(((Class) clazz).newInstance(),null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
