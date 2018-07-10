package cn.levicode.litespring.test.v2;

import cn.levicode.litespring.beans.BeanDefinition;
import cn.levicode.litespring.beans.PropertyValue;
import cn.levicode.litespring.beans.factory.config.RuntimeBeanReference;
import cn.levicode.litespring.beans.factory.support.DefaultBeanFactory;
import cn.levicode.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import cn.levicode.litespring.core.io.ClassPathResource;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class BeanDefinitionTestV2 {

    @Test
    public void testGetBeanDefinition() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);

        reader.loadBeanDefinitions(new ClassPathResource("petstore-v2.xml"));

        BeanDefinition bd = factory.getBeanDefinition("petStore");

        List<PropertyValue> propertyValues = bd.getPropertyValues();

        Assert.assertTrue(propertyValues.size() == 4);
        {
            PropertyValue propertyValue = this.getPropertyValue("accountDao", propertyValues);

            Assert.assertNotNull(propertyValue);

            Assert.assertTrue(propertyValue.getValue() instanceof RuntimeBeanReference);
        }

        {
            PropertyValue propertyValue = this.getPropertyValue("itemDao", propertyValues);

            Assert.assertNotNull(propertyValue);

            Assert.assertTrue(propertyValue.getValue() instanceof RuntimeBeanReference);
        }
    }

    private PropertyValue getPropertyValue(String name, List<PropertyValue> propertyValues) {
        for (PropertyValue pv : propertyValues) {
            if (pv.getName().equals(name)) {
                return pv;
            }
        }
        return null;
    }
}
