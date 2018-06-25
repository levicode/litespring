package cn.levicode.litespring.util;

public class ClassUtils {
    public static ClassLoader getDefaultLoader() {
        ClassLoader cl = null;
        try {
            cl = new Thread().getContextClassLoader();
        } catch (Throwable e) {
            // Cannot access thread context ClassLoader
        }
        if (cl == null) {
            // Use class loader of this class
            cl = ClassUtils.class.getClassLoader();
            if (cl == null) {
                // Use system ClassLoader
                try {
                    cl = ClassLoader.getSystemClassLoader();
                } catch (Throwable e) {
                    // Cannot access system ClassLoader return null
                }
            }
        }
        return cl;
    }
}
