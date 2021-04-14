package com.easue.processor;

import com.easue.holder.ProxyBeanHolder;
import com.easue.util.ConfigurationUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * 注册类，用于注册 我们的目标对象和通知对象之间的关系，其核心代码如下，
 * 首先实现 BeanFactoryPostProcessor ，保证其 是在对所有的bean完成扫描后，在bean的实例化之前执行，
 * 然后scan出所有的目标对象，
 * 然后建立起目标对象和通知对象的关联关系，然后放入我们的Map中
 */
@Component
public class RegisterBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    /**
     * 存放需要代理的相关信息类
     */
    public static volatile List<ProxyBeanHolder> proxyBeanHolderList = new Vector<>();

    /**
     * BeanFactory后置处理，在 所有的bean扫描完成后且在bean的实例化之前 调用
     * @param configurableListableBeanFactory 可配置的列表Bean工厂
     * @throws BeansException beanException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        // 1. 获取工厂中定义的所有bean的名称。不考虑此工厂可能参与的任何层次结构，并忽略通过Bean定义以外的其他方式注册的任何单例Bean。若为空，返回空数组。
        String[] beanDefinitionNames = configurableListableBeanFactory.getBeanDefinitionNames();
        // 循环所有的bean的名称
        for (String beanDefinitionName: beanDefinitionNames){
//            System.out.println("beanDefinitionName: " + beanDefinitionName);
            // 2. 获取指定Bean的注册BeanDefinition，获取工厂注册的原始定义对象，允许访问其属性值和构造函数参数值(可以在Bean工厂后期处理期间修改)。有点像bean.xml中的bean的id
            BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(beanDefinitionName);
            // 3. 判断 beanDefinition 是否是 AnnotatedBeanDefinition（带注释的Bean定义） 的实例
            if (beanDefinition instanceof AnnotatedBeanDefinition){
                System.out.println("beanDefinitionName: " + beanDefinitionName);
//                System.out.println("    beanDefinition: "+ beanDefinition);
                // 4. 取得 beanDefinition 上的所有的 AnnotationMetadata（注解元数据）
                AnnotationMetadata metadata = ((AnnotatedBeanDefinition) beanDefinition).getMetadata();
                Set<String> annotations = metadata.getAnnotationTypes();
//                System.out.println(annotations);
                // 5. 循环所有注解，找到aop切面注解类
                for (String annotation : annotations) {
                    // 6. AOP_POINT_ANNOTATION = com.easue.annotation.AopJ: 如果碰到@AopJ注解，扫描这一个 beanDefinition
                    if (annotation.equals(ConfigurationUtil.AOP_POINT_ANNOTATION)){
                        System.out.println("    beanDefinition: "+ beanDefinition);
                        doScan((GenericBeanDefinition)beanDefinition);
                    }
                }
            }
        }
    }

    /**
     * 扫描 所有 注解方法
     * @param beanDefinition 通用bean定义
     */
    private void doScan(GenericBeanDefinition beanDefinition) {
        try {
            // 获取此bean定义的当前bean类名 com.easue.aspect.TestAop
            String className = beanDefinition.getBeanClassName();
            // 获取具有指定名称的类的类对象  class com.easue.aspect.TestAop
            Class<?> beanDefinitionClazz = Class.forName(className);
            // 获取 该类对象(TestAop) 的公共函数(public)的方法对象数组
            Method[] methods = beanDefinitionClazz.getMethods();
            for (Method method :methods){
//                System.out.println("        method = "+method);
                // 获取 TestAop里面所有的函数 对应的注解（BeforeEasue、AfterEasue、AroundEasue）
                Annotation[] annotations = method.getAnnotations();
                for(Annotation annotation:annotations) {
                    String annotationName = annotation.annotationType().getName();//该注解的名字：com.easue.annotation.XxxEasue等
                    // 当该注解与ConfigurationUtil配置中的相同，扫描
                    if(annotationName.equals(ConfigurationUtil.BEFORE)||
                            annotationName.equals(ConfigurationUtil.AFTER)||
                            annotationName.equals(ConfigurationUtil.AROUND)) {
                        /*
                         * doScan(
                         *  com.easue.aspect.TestAop/AopAspect,
                         *  public void com.easue.aspect.TestAop.testXxx(),
                         *  @com.easue.annotation.XxxEasue(value=com.easue.dao)
                         * )
                         */
                        doScan(className, method, annotation);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 扫描出 所有 被代理的类
     * scan出所有的目标对象，然后建立起目标对象和通知对象的关联关系，然后放入Map中
     * ProxyBeanHolder：clazzName，methodName，annotationName
     * @param className
     * @param method
     * @param annotation
     */
    private void doScan(String className, Method method, Annotation annotation) {
        System.out.println("        className = "+ className);
        System.out.println("        method    = "+ method);
        System.out.println("        annotation= "+ annotation); //@com.easue.annotation.XxxEasue(value=com.easue.dao)
        ProxyBeanHolder proxyBeanHolder = new ProxyBeanHolder();// 一个bean
        proxyBeanHolder.setClazzName(className); //com.easue.aspect.TestAop
        proxyBeanHolder.setMethodName(method.getName()); // testBefore、testAfter、testAround
        proxyBeanHolder.setAnnotationName(annotation.annotationType().getName()); //com.easue.annotation.XxxEasue

        // 获取注解上的所有函数 public abstract java.lang.String com.easue.annotation.BeforeEasue.value()
        Method[] annotationMethods = annotation.annotationType().getDeclaredMethods();//方法对象的数组，表示this的所有声明的方法。
        String packagePath = null;
        for (Method annotationMethod:annotationMethods) {
            System.out.println("            annotationMethod = " + annotationMethod);
            if (annotationMethod.getName().equals("value")){
                try {
                    //packagePath = com.easue.dao，是annotation中的value值
                    packagePath = (String) annotationMethod.invoke(annotation, null);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        /*
         * 建立起目标对象和通知对象的关联关系，然后放入Map中
         */
        if (!packagePath.isEmpty()){
            String rootPath = this.getClass().getResource("/").getPath();
            String targetPackagePath = rootPath + packagePath.replace(".","/");
            File file = new File(targetPackagePath);
            //获取一个抽象路径名数组，它表示由这个抽象路径名表示的目录中的文件和目录。如果目录为空，数组将为空。如果抽象路径名不表示目录，或者发生I/O错误，则返回null。
            File[] fileList = file.listFiles();
            List<ProxyBeanHolder> proxyBeanHolderList = null;
            for (File wjFile:fileList) {
                //wjFile = D:\work-idea\SpringMyAop\target\test-classes\com\easue\dao\IndexDao.class
                if (wjFile.isFile()) {//判断是否为文件
                    // targetClass = com.easue.dao.IndexDao
                    String targetClass = packagePath+"."+wjFile.getName().replace(".class","");
                    try {
                        // 根据 com.easue.dao.IndexDao 获取 代理的全部目标对象类，clazzProxyBeanHolder是一个map<List>
                        proxyBeanHolderList = ConfigurationUtil.clazzProxyBeanHolder.get(targetClass);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if (proxyBeanHolderList==null) {
                        proxyBeanHolderList = new Vector<>();
                    }
                    proxyBeanHolderList.add(proxyBeanHolder);
                    ConfigurationUtil.clazzProxyBeanHolder.put(targetClass,proxyBeanHolderList);
                }
            }
        }
    }
}
