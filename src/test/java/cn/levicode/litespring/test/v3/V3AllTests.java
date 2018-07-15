package cn.levicode.litespring.test.v3;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BeanDefinitionTestV3.class,
        ConstructorResolverTest.class,
        ApplicationContextTestV3.class
})
public class V3AllTests {
}
