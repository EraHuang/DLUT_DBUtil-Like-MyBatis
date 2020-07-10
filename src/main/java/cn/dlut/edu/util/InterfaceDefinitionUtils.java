package cn.dlut.edu.util;

import cn.dlut.edu.cache.DaoInterfaceDefinitionCache;
import cn.dlut.edu.conf.DaoInterfaceDefinition;
import cn.dlut.edu.conf.FunctionWapper;
import cn.dlut.edu.conf.InterfaceMethodDefinition;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;


/*
   用于框架内部的调用，不要自己随意去调用，然后报一堆错
   by the way 先调getSql 再调getReturnType
 */
public class InterfaceDefinitionUtils {

    public static FunctionWapper getWapper(Class interfaceClass, Method method, Object[] args){
        DaoInterfaceDefinition daoInterfaceDefinition = DaoInterfaceDefinitionCache.get(interfaceClass.getName());
        List<InterfaceMethodDefinition> methodList = daoInterfaceDefinition.getMethodList();
        FunctionWapper functionWapper = null;
        for (InterfaceMethodDefinition interfaceMethodDefinition : methodList) {
            String functionName = interfaceMethodDefinition.getFunctionName();
            // 首先保证方法名相同
            if(match(functionName, method.getName())){
                // 其次保证参数个数
                Parameter[] parameters = interfaceMethodDefinition.getParameters();

                // 如果是没有参数的情况
                if(args == null && parameters.length == 0){
                    String sql = interfaceMethodDefinition.getAnnotationSql();
                    Class returnClass = interfaceMethodDefinition.getReturnClass();
                    String name = interfaceMethodDefinition.getAnnotationType().getName();
                    functionWapper = new FunctionWapper(sql, returnClass, name, interfaceMethodDefinition.getParameters());
                }

                // 确保参数类型相同则确定方法对应唯一sql
                else if(parameters.length == args.length){
                    int i = 0;
                    for(; i<parameters.length; i++){
                        String par = parameters[i].getType().getName().toLowerCase();
                        String arg = args[i].getClass().getName().toLowerCase();
                        if(par.contains(arg) || arg.contains(par)){
                        } else {
                            break;
                        }
                    }

                    // 如果参数比对成功
                    if(i == parameters.length){
                        String sql = interfaceMethodDefinition.getAnnotationSql();
                        Class returnClass = interfaceMethodDefinition.getReturnClass();
                        String name = interfaceMethodDefinition.getAnnotationType().getName();
                        functionWapper = new FunctionWapper(sql, returnClass, name, interfaceMethodDefinition.getParameters());
                    }
                }
            }
        }
        return functionWapper;
    }

    private static String getShortFunctionName(String detailName){
        int i = detailName.indexOf("(");
        String substring = null;
        if(i > 0){
            substring = detailName.substring(0, i);
        }
        return substring;
    }

    private static boolean match(String detailName, String shortName){
        if(detailName == null)
            return false;

        String detailNameReverse = new StringBuilder(getShortFunctionName(detailName)).reverse().toString();
        String shortNameReverse = new StringBuilder(shortName).reverse().toString();
        if(detailNameReverse.startsWith(shortNameReverse))
            return true;
        return false;
    }


}
