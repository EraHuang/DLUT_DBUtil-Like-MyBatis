package cn.dlut.edu.cache;

import cn.dlut.edu.conf.DaoInterfaceDefinition;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*
    用于描述一个dao接口的缓存，在创建代理对象的时候，你所编写的dao接口的信息会被扫描
    并缓存到这里
 */
public class DaoInterfaceDefinitionCache {

    private static Map<String, DaoInterfaceDefinition> cache = new HashMap<>();
    private static List<String> list = new LinkedList<>();


    // 在缓存中注册一个新的dao接口描述类
    public static void registered(String interfaceName, DaoInterfaceDefinition definition) throws Exception {
        if(list.contains(interfaceName))
            throw new Exception("重复注册接口");
        list.add(interfaceName);
        cache.put(interfaceName, definition);
    }


    // 获取一个dao接口的描述
    public static DaoInterfaceDefinition get(String interfaceName){
        if(list.contains(interfaceName)){
            return cache.get(interfaceName);
        }
        return null;
    }

    public static List<String> getAllInterfaceInCache(){
        return list;
    }

}
