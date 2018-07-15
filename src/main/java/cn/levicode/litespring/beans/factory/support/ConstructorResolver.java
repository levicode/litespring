package cn.levicode.litespring.beans.factory.support;

import cn.levicode.litespring.beans.BeanDefinition;
import cn.levicode.litespring.beans.ConstructorArgument;
import cn.levicode.litespring.beans.SimpleTypeConverter;
import cn.levicode.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.util.List;

public class ConstructorResolver {

    private static final Logger LOGGER = Logger.getLogger(ConstructorResolver.class);

    private final ConfigurableBeanFactory beanFactory;

    public ConstructorResolver(ConfigurableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object autowireConstructor(final BeanDefinition beanDefinition) {
        Constructor<?> constructorToUse = null;
        Object[] argsToUse = null;

        Class<?> beanClass = null;
        try {
            beanClass = beanFactory.getBeanClassLoader().loadClass(beanDefinition.getBeanClassName());
        } catch (ClassNotFoundException e) {
            throw new BeanCreationException(beanDefinition.getId(), "Instantiation of bean failed, can't resolve class", e);
        }

        Constructor<?>[] candidates = beanClass.getConstructors();

        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(beanFactory);
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();
        ConstructorArgument constructorArgument = beanDefinition.getConstructorArgument();

        for (int i = 0; i < candidates.length; i++) {
            Class<?>[] paramterTypes = candidates[i].getParameterTypes();
            if (paramterTypes.length != constructorArgument.getArgumentCount()) {
                continue;
            }
            argsToUse = new Object[paramterTypes.length];

            boolean result = valuesMatchTypes(paramterTypes,
                    constructorArgument.getArgumentValues(),
                    argsToUse,
                    valueResolver,
                    typeConverter);
            if (result) {
                constructorToUse = candidates[i];
                break;
            }
        }

        // 找不到合适的构造函数
        if (constructorToUse == null) {
            throw new BeanCreationException(beanDefinition.getId(), "can't find a apporiate constructor");
        }

        try {
            return constructorToUse.newInstance(argsToUse);
        } catch (Exception e) {
            throw new BeanCreationException(beanDefinition.getId(), "can't find a create instance using " + constructorToUse);
        }
    }

    private boolean valuesMatchTypes(Class<?>[] paramterTypes,
                                     List<ConstructorArgument.ValueHolder> argumentValues,
                                     Object[] argsToUse,
                                     BeanDefinitionValueResolver valueResolver,
                                     SimpleTypeConverter typeConverter) {
        for (int i = 0; i < paramterTypes.length; i++) {
            ConstructorArgument.ValueHolder valueHolder = argumentValues.get(i);
            // 参数原始类型，可能为RuntimeBeanReference，也可能是TypedStringValue
            Object originValue = valueHolder.getValue();

            try {
                // 获取真正的值
                Object reslovedValue = valueResolver.resolveValueIfNecessary(originValue);
                // 判断是否需要转型, 转型失败抛出异常，则说明此构造函数不可用
                Object convertedValue = typeConverter.convertIfNecessary(reslovedValue, paramterTypes[i]);
                argsToUse[i] = convertedValue;
            } catch (Exception e) {
                LOGGER.error(e);
                return false;
            }
        }
        return true;
    }
}
