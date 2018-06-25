package cn.levicode.litespring.beans.factory.support;

import cn.levicode.litespring.beans.BeanDefinition;
import cn.levicode.litespring.beans.factory.BeanDefinitionStoreException;
import cn.levicode.litespring.beans.factory.BeanFactory;
import cn.levicode.litespring.util.ClassUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory implements BeanFactory {

    public static final String ID_ARTRIBUTE = "id";

    public static final String CLASS_ARTRIBUTE = "class";

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(64);

    public DefaultBeanFactory(String configFile) {
        loadBeanDefinition(configFile);
    }

    private void loadBeanDefinition(String configFile) {
        InputStream is = null;
        try {
            ClassLoader cl = ClassUtils.getDefaultLoader();
            is = cl.getResourceAsStream(configFile);

            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);

            // <beans>
            Element root = doc.getRootElement();
            Iterator<Element> iterator = root.elementIterator();
            while (iterator.hasNext()) {
                Element element = iterator.next();
                String id = element.attributeValue(ID_ARTRIBUTE);
                String beanClassName = element.attributeValue(CLASS_ARTRIBUTE);
                BeanDefinition bd = new GenericBeanDefinition(id, beanClassName);
                this.beanDefinitionMap.put(id, bd);
            }
        } catch (DocumentException e) {
            throw new BeanDefinitionStoreException("IOException parsing XML document from " + configFile, e);
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

    @Override
    public BeanDefinition getBeanDefinition(String beanId) {
        return this.beanDefinitionMap.get(beanId);
    }

    @Override
    public Object getBean(String beanId) {
        BeanDefinition bd = this.beanDefinitionMap.get(beanId);
        if (bd == null) {
            return new BeanCreationException("Bean Definition does not exist");
        }
        ClassLoader cl = ClassUtils.getDefaultLoader();
        String beanClassName = bd.getBeanClassName();
        try {
            Class<?> clazz = cl.loadClass(beanClassName);
            return clazz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for" + beanClassName + " failed", e);
        }
    }
}
