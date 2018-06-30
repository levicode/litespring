package cn.levicode.litespring.test.v1;

import cn.levicode.litespring.context.ApplicationContext;
import cn.levicode.litespring.context.support.ClassPathXmlApplicationContext;
import cn.levicode.litespring.context.support.FileSystemXmlApplicationContext;
import cn.levicode.service.v1.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

public class ApplicationContextTest {

    @Test
    public void testGetBean() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v1.xml");
        PetStoreService petStore = (PetStoreService) ctx.getBean("petStore");

        Assert.assertNotNull(petStore);
    }

    @Test
    public void testGetBeanFromFileSystemContext() {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("/Users/zhenweili/code/litespring/src/test/resources/petstore-v1.xml");
        PetStoreService petStore = (PetStoreService) ctx.getBean("petStore");

        Assert.assertNotNull(petStore);
    }
}
