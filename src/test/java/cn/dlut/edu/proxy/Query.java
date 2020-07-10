package cn.dlut.edu.proxy;

import cn.dlut.edu.annotation.Select;

import java.sql.ResultSet;

public interface Query {
    @Select(sql = "select * froms student")
    ResultSet submit(String sql);
}
