package cn.levicode.litespring.beans.factory;

import cn.levicode.litespring.beans.BeanDefinition;

public interface BeanFactory {
    BeanDefinition getBeanDefinition(String beanId);

    Object getBean(String beanId);
}
