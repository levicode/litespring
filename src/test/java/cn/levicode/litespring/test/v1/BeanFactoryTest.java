package cn.levicode.litespring.test.v1;

import cn.levicode.litespring.beans.BeanDefinition;
import cn.levicode.litespring.beans.factory.BeanDefinitionStoreException;
import cn.levicode.litespring.beans.factory.support.BeanCreationException;
import cn.levicode.litespring.beans.factory.support.DefaultBeanFactory;
import cn.levicode.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import cn.levicode.litespring.core.io.ClassPathResource;
import cn.levicode.service.v1.PetStoreService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BeanFactoryTest {
    private DefaultBeanFactory factory = null;
    private XmlBeanDefinitionReader reader = null;

    @Before
    public void setUp() {
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
    }

    @Test
    public void testGetBean() {
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v1.xml"));
        BeanDefinition bd = factory.getBeanDefinition("petStore");

        assertTrue(bd.isSingleton());

        assertFalse(bd.isPrototype());

        assertEquals(BeanDefinition.SCOPE_DEFAULT, bd.getScope());

        assertEquals("cn.levicode.service.v1.PetStoreService", bd.getBeanClassName());

        PetStoreService petStore = (PetStoreService) factory.getBean("petStore");
        assertNotNull(petStore);

        PetStoreService petStore1 = (PetStoreService) factory.getBean("petStore");
        assertTrue(petStore.equals(petStore1));
    }

    @Test
    public void testGetPrototypeBean() {
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v1.xml"));
        BeanDefinition bd = factory.getBeanDefinition("prototypeBean");

        assertTrue(bd.isPrototype());

        assertFalse(bd.isSingleton());

        assertEquals("cn.levicode.service.v1.PetStoreService", bd.getBeanClassName());

        PetStoreService petStore = (PetStoreService) factory.getBean("prototypeBean");
        assertNotNull(petStore);

        PetStoreService petStore1 = (PetStoreService) factory.getBean("prototypeBean");
        assertFalse(petStore.equals(petStore1));
    }

    @Test
    public void testInvalidBean() {
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v1.xml"));
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
            reader.loadBeanDefinitions(new ClassPathResource("xxx.xml"));
        } catch (BeanDefinitionStoreException e) {
            return;
        }
        Assert.fail("expect BeanDefinitionStoreException");
    }
}
