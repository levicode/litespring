package cn.levicode.litespring.test;

import cn.levicode.litespring.test.v1.V1AllTests;
import cn.levicode.litespring.test.v2.V2AllTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        V1AllTests.class,
        V2AllTests.class
})
public class AllTests {
}
