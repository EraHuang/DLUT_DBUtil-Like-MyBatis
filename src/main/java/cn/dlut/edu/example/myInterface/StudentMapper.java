package cn.dlut.edu.example.myInterface;

import cn.dlut.edu.annotation.Delete;
import cn.dlut.edu.annotation.Insert;
import cn.dlut.edu.annotation.Select;
import cn.dlut.edu.annotation.Update;
import cn.dlut.edu.example.entry.Student;

import java.util.List;

/*
    这是用于展示该框架如何使用的代码，您的数据库中表的类型肯定不同，
    如果想复用我这套代码，请在数据库中创建 mydata数据库
    再创建 student 表
    表格式为

    int     String      String
    id      name        phone
 */
public interface StudentMapper {

    @Select(sql = "select * from student")
    List<Student> getAllStudent();

    @Select(sql = "select * from student where id = ?")
    Student getOneStudent(int id);

    @Insert(sql = "insert into student values (?, ?, ?)")
    boolean insert(int id, String name, String phone);

    @Delete(sql = "delete from student where id = ?")
    boolean delete(int id);

    @Update(sql = "update student set phone=? where id = ?")
    boolean updateStudent(String phone, int id);
}
