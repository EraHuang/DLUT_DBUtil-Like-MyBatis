package cn.dlut.edu.connect;

import cn.dlut.edu.util.NullArgChecker;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

/*
    数据源工厂对象
 */
class DataSourceFactoryImpl implements DataSourceFactory{
    private  Properties _prop;
    private  BasicDataSourceFactory _factory;
    private  DataSource _dataSource;

    public DataSourceFactoryImpl(Properties prop) {
        _prop = prop;
        _factory = new BasicDataSourceFactory();
    }


    public DataSource get(){
        NullArgChecker.check(_prop, "数据库配置文件为NULL");
        try {
           _dataSource = _factory.createDataSource(_prop);
        } catch (Exception e) {
           throw new RuntimeException("配置文件读取错误");
        }
        return _dataSource;
    }
}
