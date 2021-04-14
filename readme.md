# ����spring���ô�����ʵ��AOP
������� https://blog.csdn.net/baomw/article/details/84262006

### ���ô���������������
#### BeanFactoryPostProcessor �� ���Բ���beanFactory���������ڡ� 
    ������ AbstractApplicationContext.refresh()��ʱ��������beanFactory���������������׼�����������
    ��ȥִ��invokeBeanFactoryPostProcessors(beanFactory)��Ҳ����ȥִ�����ǵ�BeanFactoryPostProcessor��
    spring��ִ��PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(beanFactory, getBeanFactoryPostProcessors())��ʱ��
    �ᴫ��һ��List beanFactoryPostProcessors��Ȼ��ѭ��ȥִ��list��������ʵ����BeanFactoryPostProcessor��BeanDefinitionRegistryPostProcessor�Ķ������ط�����
    
    spring��ִ��PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(beanFactory, getBeanFactoryPostProcessors())��ʱ��
    �ᴫ��һ��List beanFactoryPostProcessors��Ȼ��ѭ��ȥִ��list��������ʵ����BeanFactoryPostProcessor��BeanDefinitionRegistryPostProcessor�Ķ������ط���

#### BeanPostProcessor �����Բ���bean���������ڡ�
    �ýӿڶ����������������ֱ� ��beanʵ����֮�� �ŵ����ǵ�����֮ǰ �� ֮�� ȥִ�У������ķ���ֵΪһ��object�����object���Ǵ����������Ķ����ˣ�
    �������λ�� ���Զ� bean��һ����̬���޸ģ��滻�ȵȲ���

#### ImportSelector ������@Importע�⣬���Զ�̬ʵ�ֽ�һ�����Ƿ���spring�������������ز���
    �Ƚ�һ��@Import���ע�⡣
    ��spring���� java���ʱ�򣬻�ֳ��������ȥ����
    1����ͨ�ࣺ���� ���� @Component��@Service��@Repository �ȵȵ���
    2������ import �������ࣺ����ַ�Ϊ���������
        a��importһ����ͨ�ࣺ@Import��A.class��
        b��importһ��Registrar���������ǵ�aop @Import(AspectJAutoProxyRegistrar.class)
        c��importһ��ImportSelector���������ü�����
    
    spring��ʲôʱ����ģ�����������
    ������ͨ�࣬spring��doScan��ClassPathBeanDefinitionScanner�У� ��ʱ�򣬾ͽ�ɨ�������java��ת���� BeanDefinition��Ȼ�����һ��BeanDefinitionMap��ȥ��
    ����@import���������������������ǵ� ConfigurationClassPostProcessor������������BeanDefinitionRegistryPostProcessor���ô�������һ��ʵ�֣�ͬʱ��Ҳ������spring�ڲ��Լ�ά����Ψһʵ���ࣨ�ų��ڲ��ࣩ����
        ���崦��import�ĺ��Ĵ������ConfigurationClassParser��processImports����if-else �����׿��Կ���spring��������import�������͵Ĵ���

### ����Ŀ¼��
    annotation�����������е��Զ���ע��
    holder:�Զ������ݽṹ�����������˵
    processor:���������еĺ��ô��������������
    selector:�����ǵ�ImportSelector��ʵ��
    util:������
----------------------------------
### ʵ��@EnableAop���ܣ���@EnableAspectJAutoProxy����
    EnableAop�ӿ�ʵ����@Import(CustomizedAopImportSelector.class)��
    ���Ի����CustomizedAopImportSelector�࣬�����Զ�����RegisterBeanFactoryPostProcessor�ĺ��ô�������
    ��������ҵ�Appconfig�ϼ���@EnableAopע�⣬��Ὣ���ǵĺ��ô�������ʵ���ཻ����spring����
    spring����ȥɨ��õ�����࣬����ȥִ�����ǵ��Զ���ĺ��ô���������ķ���������ʵ�����ǵ�aop�Ĵ���