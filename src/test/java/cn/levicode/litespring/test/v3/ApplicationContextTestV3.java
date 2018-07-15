package cn.levicode.litespring.test.v3;

import cn.levicode.litespring.context.ApplicationContext;
import cn.levicode.litespring.context.support.ClassPathXmlApplicationContext;
import cn.levicode.litespring.service.v3.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

public class ApplicationContextTestV3 {

    @Test
    public void testGetBeanProperty() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v3.xml");
        PetStoreService petStore = (PetStoreService) ctx.getBean("petStore");

        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());
        Assert.assertNotNull(petStore.getVersion());
    }
}
