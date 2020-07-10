package cn.dlut.edu.cache;

import java.io.InputStream;
import java.util.Properties;


// 数据库连接信息的缓存
public class PropertyCache {
    // 可通过流或者properties对象传入信息，两个同时传入会报错
    private static InputStream propertyStream;
    private static Properties properties;

    public static InputStream getPropertyStream() {
        return propertyStream;
    }

    public static void setPropertyStream(InputStream propertyStream) {
        PropertyCache.propertyStream = propertyStream;
    }

    public static Properties getProperties() {
        return properties;
    }

    public static void setProperties(Properties properties) {
        PropertyCache.properties = properties;
    }
}
