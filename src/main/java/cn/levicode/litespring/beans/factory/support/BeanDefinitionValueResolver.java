package cn.levicode.litespring.beans.factory.support;

import cn.levicode.litespring.beans.factory.config.RuntimeBeanReference;
import cn.levicode.litespring.beans.factory.config.TypedStringValue;

public class BeanDefinitionValueResolver {

    private final DefaultBeanFactory beanFactory;

    public BeanDefinitionValueResolver(DefaultBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object resolveValueIfNecessary(Object value) {
        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference reference = (RuntimeBeanReference) value;
            String referenceName = reference.getBeanName();
            Object bean = beanFactory.getBean(referenceName);
            return bean;
        } else if (value instanceof TypedStringValue) {
            return ((TypedStringValue) value).getValue();
        } else {
            // todo
            throw new RuntimeException("the value" + value + " has not implemented");
        }
    }
}
