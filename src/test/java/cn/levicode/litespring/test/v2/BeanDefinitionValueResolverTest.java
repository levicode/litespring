package cn.levicode.litespring.test.v2;

import cn.levicode.litespring.beans.factory.config.RuntimeBeanReference;
import cn.levicode.litespring.beans.factory.config.TypedStringValue;
import cn.levicode.litespring.beans.factory.support.BeanDefinitionValueResolver;
import cn.levicode.litespring.beans.factory.support.DefaultBeanFactory;
import cn.levicode.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import cn.levicode.litespring.core.io.ClassPathResource;
import cn.levicode.litespring.dao.v2.AccountDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BeanDefinitionValueResolverTest {

    private BeanDefinitionValueResolver resolver;

    @Before
    public void setUp() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v2.xml"));
        resolver = new BeanDefinitionValueResolver(factory);
    }

    @Test
    public void testResolveRuntimeReference() {
        RuntimeBeanReference reference = new RuntimeBeanReference("accountDao");
        Object value = resolver.resolveValueIfNecessary(reference);

        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof AccountDao);
    }

    @Test
    public void testResolveTypedStringValue() {
        TypedStringValue stringValue = new TypedStringValue("test");
        Object value = resolver.resolveValueIfNecessary(stringValue);

        Assert.assertNotNull(value);
        Assert.assertEquals("test", value);
    }
}
