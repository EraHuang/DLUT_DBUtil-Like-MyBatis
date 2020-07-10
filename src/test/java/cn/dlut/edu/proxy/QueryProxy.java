package cn.dlut.edu.proxy;

import cn.dlut.edu.connect.ConnectorProvider;
import cn.dlut.edu.entry.Student;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.RowId;

public class QueryProxy implements InvocationHandler {
    private static ConnectorProvider provider;
    private Class clazz;

    static {
        InputStream resourceAsStream = QueryProxy.class.getClassLoader().getResourceAsStream("jdbc.property");
        try {
            provider = new ConnectorProvider(resourceAsStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public QueryProxy(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().equals("submit")){
            Connection connection = provider.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(args[0].toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Field[] declaredFields = clazz.getDeclaredFields();
            Object object = clazz.newInstance();
            for (Field declaredField : declaredFields) {
                String name = declaredField.getName();
                Class<?> type = declaredField.getType();
                String typeName = type.getName().replaceAll("java.lang.", "");
                Method method1 = clazz.getMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), type);
                if(typeName.contains("int")){
                    method1.invoke(object, resultSet.getInt(name));
                } else if(typeName.contains("String")){
                    method1.invoke(object, resultSet.getString(name));
                }
                System.out.println(object);


            }
            return resultSet;
        }

        return null;

    }
}
