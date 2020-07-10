package cn.dlut.edu.conf;

import cn.dlut.edu.cache.DaoInterfaceDefinitionCache;
import cn.dlut.edu.proxy.TestInterface;

public class Test3 {
    public static void main(String[] args) {
        InterfaceRegister.register(TestInterface.class.getName(), TestInterface.class);
        DaoInterfaceDefinition daoInterfaceDefinition = DaoInterfaceDefinitionCache.get(TestInterface.class.getName());
        Class clazz = daoInterfaceDefinition.getClazz();
        System.out.println(clazz.getName());
    }
}
