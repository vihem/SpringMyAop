# ����spring���ô�����ʵ��AOP
������� https://blog.csdn.net/baomw/article/details/84262006
## ���ô������أ���������
#### BeanFactoryPostProcessor �� ���Բ���beanFactory���������ڡ� 
    ������ AnnotationConfigApplicationContext.refresh();��ʱ��������beanFactory���������������׼����������󣬻�ȥִ��invokeBeanFactoryPostProcessors(beanFactory);
    Ҳ����ȥִ�����ǵ�BeanFactoryPostProcessor
    spring��ִ��PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(beanFactory, getBeanFactoryPostProcessors());��ʱ�򣬻ᴫ��һ��List beanFactoryPostProcessors��
    Ȼ��ѭ��ȥִ��list��������ʵ����BeanFactoryPostProcessor��BeanDefinitionRegistryPostProcessor�Ķ������ط���
#### BeanPostProcessor �����Բ���bean���������ڡ�
        
#### ImportSelector ������@Importע�⣬���Զ�̬ʵ�ֽ�һ�����Ƿ���spring�������������ز���

