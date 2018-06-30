package cn.levicode.litespring.core.io;

import java.io.IOException;
import java.io.InputStream;

public interface Resource {
    InputStream getInputStream() throws IOException;

    String getDescription();
}