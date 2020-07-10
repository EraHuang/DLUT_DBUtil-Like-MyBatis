package cn.dlut.edu.conf;

import cn.dlut.edu.cache.DaoInterfaceDefinitionCache;


/*
接口注册工具，用于在缓存中缓存接口
 */
public class InterfaceRegister {
    public static void register(String interfaceName, Class interfaceClass){
        try {
            DaoInterfaceDefinitionCache.registered(interfaceName, new DaoInterfaceDefinition(interfaceClass));
        } catch (Exception e) {
            System.err.println("接口注册失败");
            e.printStackTrace();
        }
    }
}
