package cn.levicode.litespring.test.v1;

import cn.levicode.litespring.core.io.ClassPathResource;
import cn.levicode.litespring.core.io.FileSystemResource;
import cn.levicode.litespring.core.io.Resource;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class ResourceTest {

    @Test
    public void testClassPathResource() {
        Resource resource = new ClassPathResource("petstore-v1.xml");
        InputStream is = null;
        try {
            is = resource.getInputStream();
            Assert.assertNotNull(is);
        } catch (IOException e) {
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
    }

    @Test
    public void testFileSystemResource() {
        Resource resource = new FileSystemResource("/Users/zhenweili/code/litespring/src/test/resources/petstore-v1.xml");
        InputStream is = null;
        try {
            is = resource.getInputStream();
            Assert.assertNotNull(is);
        } catch (IOException e) {
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
