package com.easue.holder;

/**
 * 自定义数据结构
 * 描述通知信息
 */
public class ProxyBeanHolder {
    /**
     * 通知类名
     */
    private volatile String clazzName;
    /**
     * 通知方法名
     */
    private volatile String methodName;
    /**
     * 注解类名
     */
    private volatile String annotationName;

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getAnnotationName() {
        return annotationName;
    }

    public void setAnnotationName(String annotationName) {
        this.annotationName = annotationName;
    }
}
