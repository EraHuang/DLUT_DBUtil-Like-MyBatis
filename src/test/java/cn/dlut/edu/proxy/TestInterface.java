package cn.dlut.edu.proxy;

import cn.dlut.edu.annotation.Insert;
import cn.dlut.edu.annotation.Select;
import cn.dlut.edu.entry.Student;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface TestInterface {

    @Select(sql="select * from student where id=?")
    Student selectAll(int id);

    @Select(sql="select * from student")
    Student selectTest(int id, String name);

    @Select(sql="select * from student")
    int select();
}
