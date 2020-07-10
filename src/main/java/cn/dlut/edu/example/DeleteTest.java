package cn.dlut.edu.example;

import cn.dlut.edu.boot.DataBaseAccessBeanFactory;
import cn.dlut.edu.example.entry.Student;
import cn.dlut.edu.example.myInterface.StudentMapper;

import java.io.InputStream;
import java.util.List;
/*
    这是用于展示该框架如何使用的代码，您的数据库中表的类型肯定不同，
    如果想复用我这套代码，请在数据库中创建 mydata数据库
    再创建 student 表
    表格式为

    int     String      String
    id      name        phone
 */

// 删除示例代码
public class DeleteTest {
    public static void main(String[] args) {
        // 利用classLoader从类路径下以流的方式读取jdbc.property文件
        InputStream resourceAsStream = DeleteTest.class.getClassLoader().getResourceAsStream("jdbc.property");
        // 构建一个DataBaseAccessBeanFactory对象
        DataBaseAccessBeanFactory factory = new DataBaseAccessBeanFactory().build(resourceAsStream);
        StudentMapper proxyObject = (StudentMapper) factory.getProxyObject(StudentMapper.class);
        boolean delete = proxyObject.delete(4);
        System.out.println(delete);

        List<Student> allStudent = proxyObject.getAllStudent();
        for (Student student : allStudent) {
            System.out.println(student);
        }
    }
}
