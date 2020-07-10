package cn.dlut.edu.conf;

import cn.dlut.edu.annotation.Delete;
import cn.dlut.edu.annotation.Insert;
import cn.dlut.edu.annotation.Select;
import cn.dlut.edu.annotation.Update;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/*
用于描述用户在接口中编写的方法
 */
public class InterfaceMethodDefinition {
    // 方法上的注解
    private Annotation annotation = null;
    // 返回值
    private Class returnClass;
    // 方法名
    private String functionName;
    // 传入的参数
    private Parameter[] parameters = null;
    // sql语句
    private String annotationSql;
    // 注解的类型
    private Class annotationType;


    public InterfaceMethodDefinition(Method method) throws Exception {
        if(method == null)
            throw new NullPointerException("参数method 不可为空");

        Annotation[] annotations = method.getDeclaredAnnotations();
        // 默认用户编写在接口里的方法上的注解只能有一个
        if(annotations.length > 1)
            throw new Exception("请勿在持久化接口方法上使用多个注解");
        else if(annotations.length == 1)
            this.annotation = annotations[0];

        this.returnClass = method.getReturnType();
        this.functionName = method.toGenericString();
        this.parameters = method.getParameters();

        getSql(method);
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public Class getReturnClass() {
        return returnClass;
    }

    public String getFunctionName() {
        return functionName;
    }

    public Parameter[] getParameters() {
        return parameters;
    }

    public String getAnnotationSql() {
        return annotationSql;
    }

    public Class getAnnotationType() {
        return annotationType;
    }

    // 从方法中获取注解类型，并获取注解中的sql语句
    // 其实就是判断是select insert update delete中的哪一个
    // 然后从注解中获取sql语句
    private void getSql(Method method){
        Select select = method.getAnnotation(Select.class);
        Insert insert = method.getAnnotation(Insert.class);
        Update update = method.getAnnotation(Update.class);
        Delete delete = method.getAnnotation(Delete.class);

        if(select != null){
            annotationSql = select.sql();
            annotationType = Select.class;
        } else if(insert != null){
            annotationSql = insert.sql();
            annotationType = Insert.class;
        } else if(update != null){
            annotationSql = update.sql();
            annotationType = Update.class;
        } else if(delete != null){
            annotationSql = delete.sql();
            annotationType = Delete.class;
        }
    } 
}
