# 利用spring后置处理器实现AOP
详情见： https://blog.csdn.net/baomw/article/details/84262006

### 后置处理器，有三个：
#### BeanFactoryPostProcessor ： 可以插手beanFactory的生命周期。 
    在我们 AbstractApplicationContext.refresh()的时候，在我们beanFactory被创建出来后，相关准备工作做完后，
    会去执行invokeBeanFactoryPostProcessors(beanFactory)，也就是去执行我们的BeanFactoryPostProcessor；
    spring在执行PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(beanFactory, getBeanFactoryPostProcessors())的时候，
    会传入一个List beanFactoryPostProcessors，然后循环去执行list里面所有实现了BeanFactoryPostProcessor和BeanDefinitionRegistryPostProcessor的对象的相关方法。
    
    spring在执行PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(beanFactory, getBeanFactoryPostProcessors())的时候，
    会传入一个List beanFactoryPostProcessors，然后循环去执行list里面所有实现了BeanFactoryPostProcessor和BeanDefinitionRegistryPostProcessor的对象的相关方法

#### BeanPostProcessor ：可以插手bean的生命周期。
    该接口定义了两个方法，分别 在bean实例化之后 放到我们的容器之前 和 之后 去执行，方法的返回值为一个object，这个object就是存在于容器的对象了，
    所以这个位置 可以对 bean做一个动态的修改，替换等等操作

#### ImportSelector ：借助@Import注解，可以动态实现将一个类是否交由spring管理，常用作开关操作
    先讲一下@Import这个注解。
    在spring处理 java类的时候，会分成四种情况去处理：
    1）普通类：就是 加了 @Component，@Service，@Repository 等等的类
    2）处理 import 进来的类：这里，又分为三种情况：
        a）import一个普通类：@Import（A.class）
        b）import一个Registrar：比如我们的aop @Import(AspectJAutoProxyRegistrar.class)
        c）import一个ImportSelector：具体妙用见下文
    
    spring在什么时候处理的，大致叙述：
    对于普通类，spring在doScan（ClassPathBeanDefinitionScanner中） 的时候，就将扫描出来的java类转换成 BeanDefinition，然后放入一个BeanDefinitionMap中去；
    对于@import的三种情况，处理就在我们的 ConfigurationClassPostProcessor（该类是我们BeanDefinitionRegistryPostProcessor后置处理器的一个实现，同时这也是我们spring内部自己维护的唯一实现类（排除内部类）），
        具体处理import的核心代码见（ConfigurationClassParser的processImports），if-else 很容易可以看出spring对于我们import三种类型的处理。

### 工程目录：
    annotation：放我们所有的自定义注解
    holder:自定义数据结构，具体类后面说
    processor:放我们所有的后置处理器及代理相关
    selector:放我们的ImportSelector的实现
    util:工具类
----------------------------------
### 实现@EnableAop功能，即@EnableAspectJAutoProxy功能
    EnableAop接口实现了@Import(CustomizedAopImportSelector.class)，
    所以会调用CustomizedAopImportSelector类，该类自动调用RegisterBeanFactoryPostProcessor的后置处理器。
    即：如果我的Appconfig上加了@EnableAop注解，则会将我们的后置处理器的实现类交给了spring管理，
    spring才能去扫描得到这个类，才能去执行我们的自定义的后置处理器里面的方法，才能实现我们的aop的代理。