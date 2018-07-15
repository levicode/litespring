package cn.levicode.litespring.beans.factory.xml;

import cn.levicode.litespring.beans.BeanDefinition;
import cn.levicode.litespring.beans.ConstructorArgument;
import cn.levicode.litespring.beans.PropertyValue;
import cn.levicode.litespring.beans.factory.BeanDefinitionStoreException;
import cn.levicode.litespring.beans.factory.config.RuntimeBeanReference;
import cn.levicode.litespring.beans.factory.config.TypedStringValue;
import cn.levicode.litespring.beans.factory.support.BeanDefinitionRegistry;
import cn.levicode.litespring.beans.factory.support.GenericBeanDefinition;
import cn.levicode.litespring.core.io.Resource;
import cn.levicode.litespring.util.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class XmlBeanDefinitionReader {

    private BeanDefinitionRegistry registry;

    public static final String ID_ATTRIBUTE = "id";

    public static final String CLASS_ATTRIBUTE = "class";

    public static final String SCOPE_ATTRIBUTE = "scope";

    public static final String PROPERTY_ATTRIBUTE = "property";

    public static final String NAME_ATTRIBUTE = "name";

    public static final String VALUE_ATTRIBUTE = "value";

    public static final String REF_ATTRIBUTE = "ref";

    public static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";

    public static final String TYPE_ATTRIBUTE = "type";

    private static final Logger LOGGER = Logger.getLogger(XmlBeanDefinitionReader.class);

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void loadBeanDefinitions(Resource resource) {
        InputStream is = null;
        try {
            is = resource.getInputStream();

            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);

            // <beans>
            Element root = doc.getRootElement();
            Iterator<Element> iterator = root.elementIterator();
            while (iterator.hasNext()) {
                Element element = iterator.next();
                String id = element.attributeValue(ID_ATTRIBUTE);
                String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition bd = new GenericBeanDefinition(id, beanClassName);
                if (element.attribute(SCOPE_ATTRIBUTE) != null) {
                    bd.setScope(element.attributeValue(SCOPE_ATTRIBUTE));
                }
                parseConstructorArgElements(element, bd);
                parsePropertyElement(element, bd);
                registry.registerBeanDefinition(id, bd);
            }
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("IOException parsing XML document from " + resource.getDescription(), e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void parseConstructorArgElements(Element beanElement, BeanDefinition beanDefinition) {
        Iterator<Element> iterator = beanElement.elementIterator(CONSTRUCTOR_ARG_ELEMENT);
        while (iterator.hasNext()) {
            Element constructorElement = iterator.next();
            parseConstructorArgElement(constructorElement, beanDefinition);
        }
    }

    public void parseConstructorArgElement(Element constructorElement, BeanDefinition beanDefinition) {
        String name = constructorElement.attributeValue(NAME_ATTRIBUTE);
        String type = constructorElement.attributeValue(TYPE_ATTRIBUTE);
        Object value = parsePropertyValue(constructorElement, beanDefinition, null);
        ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder(value);
        if (StringUtils.hasLength(name)) {
            valueHolder.setName(name);
        }
        if (StringUtils.hasLength(type)) {
            valueHolder.setType(type);
        }
        beanDefinition.getConstructorArgument().addArgumentValue(valueHolder);
    }


    public void parsePropertyElement(Element beanElement, BeanDefinition beanDefinition) {
       Iterator<Element> iterator = beanElement.elementIterator(PROPERTY_ATTRIBUTE);
       while (iterator.hasNext()) {
           Element propertyElement = iterator.next();
           String propertyName = propertyElement.attributeValue(NAME_ATTRIBUTE);
           if (!StringUtils.hasLength(propertyName)) {
               LOGGER.fatal("Tag 'property' must have a 'name' attribute");
               return;
           }
           Object value = parsePropertyValue(propertyElement, beanDefinition, propertyName);
           PropertyValue propertyValue = new PropertyValue(propertyName, value);
           beanDefinition.getPropertyValues().add(propertyValue);
       }
    }

    public Object parsePropertyValue(Element propertyElement, BeanDefinition beanDefinition, String propertyName) {
        String elementName = (propertyName != null ? "<property> element for property '" + propertyName + "'"
                : "<constructor-arg> element");

        boolean hasRefAttribute = (propertyElement.attribute(REF_ATTRIBUTE) != null);
        boolean hasValueAttribute = (propertyElement.attribute(VALUE_ATTRIBUTE) != null);
        if (hasRefAttribute) {
            String refName = propertyElement.attributeValue(REF_ATTRIBUTE);
            if (!StringUtils.hasText(refName)) {
                LOGGER.error(elementName + " contains empty 'ref' attribute");
            }
            RuntimeBeanReference reference = new RuntimeBeanReference(refName);
            return reference;
        } else if (hasValueAttribute) {
            TypedStringValue valueHolder = new TypedStringValue(propertyElement.attributeValue(VALUE_ATTRIBUTE));
            return valueHolder;
        } else {
            throw new RuntimeException(elementName + " must specify a ref or value");
        }
    }
}
