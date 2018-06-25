package cn.levicode.litespring.test.v1;

import cn.levicode.litespring.beans.BeanDefinition;
import cn.levicode.litespring.beans.factory.BeanDefinitionStoreException;
import cn.levicode.litespring.beans.factory.BeanFactory;
import cn.levicode.litespring.beans.factory.support.BeanCreationException;
import cn.levicode.litespring.beans.factory.support.DefaultBeanFactory;
import cn.levicode.service.v1.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BeanFactoryTest {
    @Test
    public void testGetBean() {
        BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
        BeanDefinition bd = factory.getBeanDefinition("petStore");

        assertEquals("cn.levicode.service.v1.PetStoreService", bd.getBeanClassName());

        PetStoreService petStore = (PetStoreService) factory.getBean("petStore");
        assertNotNull(petStore);
    }

    @Test
    public void testInvalidBean() {
        BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
        try {
            factory.getBean("invalidBean");
        } catch (BeanCreationException e) {
            return;
        }
        Assert.fail("expect BeanCreationException");
    }

    @Test
    public void testInvalidXml() {
        try {
            new DefaultBeanFactory("xxx.xml");
        } catch (BeanDefinitionStoreException e) {
            return;
        }
        Assert.fail("expect BeanDefinitionStoreException");
    }
}
