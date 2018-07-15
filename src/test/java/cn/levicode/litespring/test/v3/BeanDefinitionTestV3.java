package cn.levicode.litespring.test.v3;

import cn.levicode.litespring.beans.BeanDefinition;
import cn.levicode.litespring.beans.ConstructorArgument;
import cn.levicode.litespring.beans.factory.config.RuntimeBeanReference;
import cn.levicode.litespring.beans.factory.config.TypedStringValue;
import cn.levicode.litespring.beans.factory.support.DefaultBeanFactory;
import cn.levicode.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import cn.levicode.litespring.core.io.ClassPathResource;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class BeanDefinitionTestV3 {

    @Test
    public void testConstructorArgument() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v3.xml"));

        BeanDefinition bd = factory.getBeanDefinition("petStore");
        ConstructorArgument args = bd.getConstructorArgument();
        List<ConstructorArgument.ValueHolder> valueHolders = args.getArgumentValues();

        Assert.assertEquals(3, valueHolders.size());

        RuntimeBeanReference ref1 = (RuntimeBeanReference) valueHolders.get(0).getValue();
        Assert.assertEquals("accountDao", ref1.getBeanName());

        RuntimeBeanReference ref2 = (RuntimeBeanReference) valueHolders.get(1).getValue();
        Assert.assertEquals("itemDao", ref2.getBeanName());

        TypedStringValue stringValue = (TypedStringValue) valueHolders.get(2).getValue();
        Assert.assertEquals("1", stringValue.getValue());
    }
}
