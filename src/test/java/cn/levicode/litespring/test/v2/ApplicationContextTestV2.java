package cn.levicode.litespring.test.v2;

import cn.levicode.litespring.context.ApplicationContext;
import cn.levicode.litespring.context.support.ClassPathXmlApplicationContext;
import cn.levicode.litespring.dao.v2.AccountDao;
import cn.levicode.litespring.dao.v2.ItemDao;
import cn.levicode.litespring.service.v2.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

public class ApplicationContextTestV2 {

    @Test
    public void testGetBeanProperty() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v2.xml");
        PetStoreService petStore = (PetStoreService) ctx.getBean("petStore");

        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());

        Assert.assertTrue(petStore.getAccountDao() instanceof AccountDao);
        Assert.assertTrue(petStore.getItemDao() instanceof ItemDao);
        Assert.assertEquals("levicode", petStore.getOwner());
        Assert.assertEquals(2, petStore.getVersion());
    }
}
