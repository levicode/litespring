package cn.levicode.litespring.beans.factory.support;

import cn.levicode.litespring.beans.BeanDefinition;

public interface BeanDefinitionRegistry {
    BeanDefinition getBeanDefinition(String beanId);

    void registerBeanDefinition(String beanId, BeanDefinition beanDefinition);
}
