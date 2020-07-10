package cn.dlut.edu.conf;

import java.lang.reflect.Parameter;


// 该类用于包装一个更精简的用户编写的查询方法
public class FunctionWapper {

    // 方法上的sql语句
    private String sql;
    // 方法的返回值
    private Class returnType;

    // 注解的类型
    private String annotationName;
    // 传入的参数
    private Parameter[] parameters;

    public FunctionWapper(String sql, Class returnType, String annotationName, Parameter[] parameters) {
        this.sql = sql;
        this.returnType = returnType;
        this.annotationName = annotationName;
        this.parameters = parameters;
    }

    public String getSql() {
        return sql;
    }

    public Class getReturnType() {
        return returnType;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setReturnType(Class returnType) {
        this.returnType = returnType;
    }

    public void setAnnotationName(String annotationName) {
        this.annotationName = annotationName;
    }

    public void setParameters(Parameter[] parameters) {
        this.parameters = parameters;
    }

    public String getAnnotationName() {
        return annotationName;
    }

    public Parameter[] getParameters() {
        return parameters;
    }
}
