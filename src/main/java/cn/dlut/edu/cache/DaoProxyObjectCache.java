package cn.dlut.edu.cache;

import java.util.HashMap;
import java.util.Map;

/*
    用于缓存生成的代理对象，再次获取的时候将不再创建，直接从缓存中获取
 */
public class DaoProxyObjectCache {
    private static Map<String, Object> cahce = new HashMap<>();

    public static void putCache(String interfaceName, Object proxyClassObject){
        cahce.put(interfaceName, proxyClassObject);
    }

    public static Object get(String interfaceName){
        return cahce.get(interfaceName);
    }
}
