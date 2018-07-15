package cn.levicode.litespring.test.v3;

import cn.levicode.litespring.beans.BeanDefinition;
import cn.levicode.litespring.beans.factory.support.ConstructorResolver;
import cn.levicode.litespring.beans.factory.support.DefaultBeanFactory;
import cn.levicode.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import cn.levicode.litespring.core.io.ClassPathResource;
import cn.levicode.litespring.service.v3.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

public class ConstructorResolverTest {

    @Test
    public void testAutowireConstructor() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v3.xml"));

        BeanDefinition bd = factory.getBeanDefinition("petStore");

        ConstructorResolver resolver = new ConstructorResolver(factory);

        PetStoreService petStore = (PetStoreService) resolver.autowireConstructor(bd);

        Assert.assertEquals(1, petStore.getVersion());
        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());
    }
}
