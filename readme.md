# 利用spring后置处理器实现AOP
详情见： https://blog.csdn.net/baomw/article/details/84262006
## 后置处理器呢，有三个：
#### BeanFactoryPostProcessor ： 可以插手beanFactory的生命周期。 
    在我们 AnnotationConfigApplicationContext.refresh();的时候，在我们beanFactory被创建出来后，相关准备工作做完后，会去执行invokeBeanFactoryPostProcessors(beanFactory);
    也就是去执行我们的BeanFactoryPostProcessor
    spring在执行PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(beanFactory, getBeanFactoryPostProcessors());的时候，会传入一个List beanFactoryPostProcessors；
    然后循环去执行list里面所有实现了BeanFactoryPostProcessor和BeanDefinitionRegistryPostProcessor的对象的相关方法
#### BeanPostProcessor ：可以插手bean的生命周期。
        
#### ImportSelector ：借助@Import注解，可以动态实现将一个类是否交由spring管理，常用作开关操作

