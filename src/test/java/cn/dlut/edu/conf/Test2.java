package cn.dlut.edu.conf;

import cn.dlut.edu.proxy.TestInterface;

import java.util.List;

public class Test2 {
    public static void main(String[] args) {
        DaoInterfaceDefinition daoInterfaceDefinition = new DaoInterfaceDefinition(TestInterface.class);
        Class clazz = daoInterfaceDefinition.getClazz();
        System.out.println(clazz.getName());
        List<InterfaceMethodDefinition> methodList = daoInterfaceDefinition.getMethodList();
        for (InterfaceMethodDefinition interfaceMethodDefinition : methodList) {
            String functionName = interfaceMethodDefinition.getFunctionName();
            System.out.println(functionName);
        }
    }
}
