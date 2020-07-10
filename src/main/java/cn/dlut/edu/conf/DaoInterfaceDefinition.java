package cn.dlut.edu.conf;

import cn.dlut.edu.util.AnnotationJudgment;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

// 描述一个用户编写的dao接口
public class DaoInterfaceDefinition {
    // 接口的类型
    private Class clazz;
    // 用于描述接口中各个方法描述信息的List
    private List<InterfaceMethodDefinition> list = new LinkedList<>();


    public Class getClazz() {
        return clazz;
    }

    public List<InterfaceMethodDefinition> getMethodList() {
        return list;
    }


    /*
    传入一个接口类型，将接口中所有带有select insert update delete注解的 扫描一遍
    并获取指定的信息，保存在InterfaceMethodDefinition对象中，
    DaoInterfaceDefinition用于描述一个interface, InterfaceMethodDefinition用于
    描述一个方法，一个Interface有一个或多个方法，所以一个DaoInterfaceDefinition中
    有多个InterfaceMethodDefinition
     */
    public <T> DaoInterfaceDefinition(Class<T> clazz) {
        this.clazz = clazz;
        // 获取接口的所有方法
        Method[] declaredMethods = clazz.getDeclaredMethods();

        // 便利所有方法，从中获取我们需要的信息
        for (Method declaredMethod : declaredMethods) {
            // 获取方法中所有的注解
            Annotation[] declaredAnnotations = declaredMethod.getDeclaredAnnotations();
            boolean hasAnnotation = declaredAnnotations.length > 0;
            // 在注解存在的情况下才扫描该方法
            if(hasAnnotation){
                try {
                    for (Annotation declaredAnnotation : declaredAnnotations) {
                       if(AnnotationJudgment.hasDefinedAnnotation(declaredAnnotation.annotationType().getName())) {
                           // 只要将Method（方法对象）传给InterfaceMethodDefinition
                           // 就可以了,由它进行更细致的解析
                           list.add(new InterfaceMethodDefinition(declaredMethod));
                           break;
                       }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }



}
