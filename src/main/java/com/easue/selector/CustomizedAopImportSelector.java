package com.easue.selector;

import com.easue.processor.RealizedAopBeanPostProcessor;
import com.easue.processor.RegisterBeanFactoryPostProcessor;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 自定义aop实现，提交给spring处理的类
 *  = AspectJAutoProxyRegistrar
 */
public class CustomizedAopImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{
                RealizedAopBeanPostProcessor.class.getName(), RegisterBeanFactoryPostProcessor.class.getName()
        };
    }
}
