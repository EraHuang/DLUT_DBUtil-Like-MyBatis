package cn.dlut.edu.connect;

import javax.sql.DataSource;
import java.sql.ResultSet;

public interface DataSourceFactory {

    // 返回一个DataSource对象
    public DataSource get();
}
