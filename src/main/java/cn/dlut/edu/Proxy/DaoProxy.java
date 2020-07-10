package cn.dlut.edu.Proxy;

import cn.dlut.edu.cache.DaoInterfaceDefinitionCache;
import cn.dlut.edu.cache.PropertyCache;
import cn.dlut.edu.conf.DaoInterfaceDefinition;
import cn.dlut.edu.conf.FunctionWapper;
import cn.dlut.edu.connect.ConnectorProvider;
import cn.dlut.edu.submit.Submiter;
import cn.dlut.edu.util.InterfaceDefinitionUtils;

import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;

// Dao接口的代理对象
public class DaoProxy implements InvocationHandler {
    private InputStream propertyStream;
    private Properties properties;
    private Class interfaceClass;
    private ConnectorProvider provider;

    public DaoProxy(Class interfaceClass){
        if(interfaceClass == null)
            throw new NullPointerException("输入的类类型不能为null");
        this.interfaceClass = interfaceClass;
        propertyStream = PropertyCache.getPropertyStream();
        properties = PropertyCache.getProperties();
        if(propertyStream == null && propertyStream == null)
            throw new RuntimeException("必须先对DataBaseAccessBeanFactory调用build方法来构建信息");
        else if(properties != null && propertyStream != null)
            throw new RuntimeException("重复定义了信息，请不要调用两次build方法");

        if(properties == null) {
            try {
                provider = new ConnectorProvider(propertyStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                provider = new ConnectorProvider(properties);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    @Override
        public Object invoke(Object proxy, Method method, Object[] args){
        Connection connection = provider.getConnection();
        DaoInterfaceDefinition daoInterfaceDefinition = DaoInterfaceDefinitionCache.get(interfaceClass.getName());
        FunctionWapper wapper = InterfaceDefinitionUtils.getWapper(interfaceClass, method, args);
        Submiter submiter = new Submiter(connection, wapper, args, method, interfaceClass);
        Object submit = submiter.submit();
        if(List.class.isInstance(submit)){
            List list = (List) submit;

            Class returnType = wapper.getReturnType();
            if(returnType.equals(List.class)){
                return list;
            } else if(list.isEmpty()){
                return null;
            } else {
                return list.get(0);
            }
        } else {
            return (Boolean) submit;
        }
    }
}
