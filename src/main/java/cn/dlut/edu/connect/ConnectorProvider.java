package cn.dlut.edu.connect;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.Properties;


// 连接提供者
public class ConnectorProvider{
    private static DataSourceFactory factory;

    public ConnectorProvider(DataSourceFactory factory){
        this.factory = factory;
    }



    public ConnectorProvider(Properties prop) throws Exception {
        if(!verifyProp(prop))
            throw new Exception("请确保在属性中至少设置了driverClassName,url,username和password");
        factory = new DataSourceFactoryImpl(prop);
    }


    // 从流中读取配置
    public ConnectorProvider(InputStream inputStream) throws Exception {
        Properties properties = new Properties();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line  = "";
        try{
            while((line=reader.readLine()) != null){
                int i = line.indexOf("=");
                properties.setProperty(line.substring(0, i), line.substring(i+1, line.length()));
            }
        } catch (Exception e){
            throw new RuntimeException("配置文件读取错误,请确认Property文件格式");
        } finally {
            inputStream.close();
        }

//        if(!verifyProp(properties))
//            throw new Exception("请确保在属性中至少设置了driverClassName,url,username和password");
        if(!verifyProp(properties))
            throw new Exception("请确保在属性中至少设置了driverClassName,url,username和password");
        factory = new DataSourceFactoryImpl(properties);
    }


    // 确认属性中是否配置了基本的连接信息否包含最基本的信息
    private static boolean verifyProp(Properties prop){
       if(prop.getProperty("driverClassName") == null || prop.getProperty("url") == null
            || prop.getProperty("username") == null || prop.getProperty("password") == null)
           return false;
       return true;
    }

    public Connection getConnection(){
        Connection connection = null;
        try{
            connection = factory.get().getConnection();
        } catch (Exception e){
            throw new RuntimeException("获取连接失败");
        }
        return connection;
    }



}
