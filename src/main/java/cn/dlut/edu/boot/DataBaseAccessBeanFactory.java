package cn.dlut.edu.boot;

import cn.dlut.edu.Proxy.DaoProxy;
import cn.dlut.edu.cache.DaoInterfaceDefinitionCache;
import cn.dlut.edu.cache.DaoProxyObjectCache;
import cn.dlut.edu.cache.PropertyCache;
import cn.dlut.edu.conf.InterfaceRegister;
import cn.dlut.edu.util.NullArgChecker;

import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.util.Properties;


/*
    产生可间接操作数据库代理对象的工厂
    第一步首先就要考虑连接参数
    把DataBaseAccessBeanFactory new 出来了就要调用build方法来传入连接参数
 */
public class DataBaseAccessBeanFactory {

    // 已流的形式读取property文件
    public DataBaseAccessBeanFactory build(InputStream propertyStream){
        NullArgChecker.check(propertyStream, "信息流不能为null");
        // 向连接参数缓存中放入连接参数
        PropertyCache.setPropertyStream(propertyStream);
        return this;
    }

    public DataBaseAccessBeanFactory build(Properties properties){
        NullArgChecker.check(properties, "properties不能为null");
        // 向连接参数缓存中放入连接参数
        PropertyCache.setProperties(properties);
        return this;
    }


    // 从工厂中获取代理对象的方法
    public Object getProxyObject(Class daoInterface){
        Object proxy = null;

        // 获取代理对象类型，并判断在缓存中是否存在，如果存在，将不再创建，直接从缓存获取
        String interfaceName = daoInterface.getName();

        if(DaoInterfaceDefinitionCache.get(interfaceName) != null){
            // 从缓存中获取
            return DaoProxyObjectCache.get(interfaceName);
        } else {
            // 创建一个新的代理对象，并加入缓存
            try{
                // 实例化一个实现了用户接口的代理对象
                proxy = Proxy.newProxyInstance(DataBaseAccessBeanFactory.class.getClassLoader(), new Class[]{daoInterface}, new DaoProxy(daoInterface));
                // 在缓存中注册代理对象实现接口的信息
                InterfaceRegister.register(interfaceName, daoInterface);
                // 将代理对象放入缓存中，再次调用时无需再动态生成
                DaoProxyObjectCache.putCache(interfaceName, proxy);
            } catch (Exception e){
                System.err.println("代理对象生成失败");
            }
        }
        return proxy;
    }
}
