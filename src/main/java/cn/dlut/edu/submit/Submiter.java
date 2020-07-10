package cn.dlut.edu.submit;

import cn.dlut.edu.conf.FunctionWapper;
import cn.dlut.edu.util.MethodBuilder;

import java.lang.reflect.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class Submiter {
    private Connection connection;
    private FunctionWapper wapper;
    private Object[] args;
    private Method method;
    private Class interfaceClass;
    private Class internalReturnType = null;


    public Submiter(Connection connection, FunctionWapper wapper, Object[] args, Method method, Class interfaceClass) {
        this.connection = connection;
        this.wapper = wapper;
        this.args = args;
        this.method = method;
        this.interfaceClass = interfaceClass;
    }

    public Object submit() {
        String sql = wapper.getSql();
        String annotationName = wapper.getAnnotationName();
        Class returnType = wapper.getReturnType();

        if(returnType.getName().toLowerCase().contains("list")){
            Method[] declaredMethods = interfaceClass.getDeclaredMethods();
            Method m = null;
            for (Method declaredMethod : declaredMethods) {
                if(declaredMethod.getName().equals(method.getName())){
                    m = declaredMethod;
                    break;
                }
            }
            ParameterizedType genericReturnType = (ParameterizedType) m.getGenericReturnType();
            Type actualTypeArgument = genericReturnType.getActualTypeArguments()[0];
            String typeName = actualTypeArgument.getTypeName();
            Class<?> clazz = null;
            try {
                clazz = Class.forName(typeName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            returnType = clazz;
            internalReturnType = clazz;
        }

        int parameterNumber = count(sql, "\\?");


        if (args != null && parameterNumber != args.length){
            throw new RuntimeException("sql语句中的参数与输入的参数不匹配");
        }

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            if(parameterNumber != 0){
                for(int i=0; i<args.length; i++){
                    String name = args[i].getClass().getName().toLowerCase();
                    if(name.contains("int")){
                        statement.setInt(i+1, (int) args[i]);
                    } else if(name.contains("long")){
                        statement.setLong(i+1, (long) args[i]);
                    } else if(name.contains("double")){
                        statement.setDouble(i+1, (double) args[i]);
                    } else if(name.contains("string")){
                        statement.setString(i+1, args[i].toString());
                    } else if(name.contains("short")){
                        statement.setShort(i+1, (short) args[i]);
                    } else if(name.contains("date")){
                        statement.setDate(i+1, (Date) args[i]);
                    } else if(name.contains("byte")){
                        statement.setByte(i+1, (byte) args[i]);
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }


        // 查询回来的对象需要进行封装，根据返回对象的个数分别封装为一个对象或者一个对象列表
        if(annotationName.toLowerCase().contains("select")){
            List<Object> list = new LinkedList<>();
            ResultSet resultSet = null;
            ResultSetMetaData metaData = null;
            try {
                resultSet = statement.executeQuery();
                metaData = resultSet.getMetaData();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }

            String[] columnNames = getColumnNames(metaData);
            Class[] filedClass = getFiledClass(columnNames);

            Object proxyObject = null;
            try {
                while(resultSet.next()){
                    proxyObject = returnType.newInstance();
                    for(int i=0; i<columnNames.length; i++){
                        Class clazz = filedClass[i];
                        String name = columnNames[i];
                        String methodName = MethodBuilder.setMethodBuilder(name);
                        Method method = returnType.getMethod(methodName, clazz);
                        if(clazz.getName().contains("byte")){
                            byte returnByte = resultSet.getByte(name);
                            method.invoke(proxyObject, returnByte);
                        } else if(clazz.getName().contains("short")){
                            short returnShort = resultSet.getShort(name);
                            method.invoke(proxyObject, returnShort);
                        } else if(clazz.getName().contains("String")){
                            String returnString = resultSet.getString(name);
                            method.invoke(proxyObject, returnString);
                        } else if(clazz.getName().contains("int")){
                            int returnInt = resultSet.getInt(name);
                            method.invoke(proxyObject, returnInt);
                        } else if(clazz.getName().contains("Date")){
                            Date returnDate = resultSet.getDate(name);
                            method.invoke(proxyObject, returnDate);
                        }
                    }
                    list.add(proxyObject);
                }
            } catch (InstantiationException | NoSuchMethodException | SQLException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }


            return list;
        }

        // 用于update insert delete这用没有返回数据.不用对返回数据进行封装的方法
        else {
            boolean execute = false;
            try {
                execute = statement.execute();
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }

            return !execute;
        }

    }

    private int count(String sql, String mark){
        String s = sql.replaceAll(mark, "");
        return sql.length() - s.length();
    }


    private String[] getColumnNames(ResultSetMetaData metaData){
        int columnCount;
        String[] columnNames = null;
        try {
            columnCount = metaData.getColumnCount();
            columnNames = new String[columnCount];
            for(int i=0; i<columnCount; i++){
                columnNames[i] = metaData.getColumnName(i+1);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return columnNames;
    }

    private Class[] getFiledClass(String[] filedNames){
        Class[] classes = new Class[filedNames.length];
        Class retunType = internalReturnType != null ? internalReturnType : wapper.getReturnType();
        for(int i=0; i<filedNames.length; i++){
            try {
                Field declaredField = retunType.getDeclaredField(filedNames[i]);
                classes[i] = declaredField.getType();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

        }
        return classes;
    }


//    private  Class classFormat(Class clazz){
//        if(clazz.getName().contains("int"))
//            return Integer.class;
//        else if(clazz.getName().contains("byte"))
//            return Byte.class;
//        else if(clazz.getName().contains("char"))
//            return Character.class;
//        else if(clazz.getName().contains("short"))
//            return Short.class;
//        else if(clazz.getName().contains("float"))
//            return Float.class;
//        else if(clazz.getName().contains("double"))
//            return Double.class;
//        else
//            return clazz;
//
//    }

}
