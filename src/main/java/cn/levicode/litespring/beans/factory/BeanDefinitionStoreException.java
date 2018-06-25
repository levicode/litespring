package cn.levicode.litespring.beans.factory;

import cn.levicode.litespring.beans.BeansException;

public class BeanDefinitionStoreException extends BeansException {

    public BeanDefinitionStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
