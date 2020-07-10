一款类似于mybatis的持久层框架，比mybatis使用起来更为简单
名为DBUtil，实际上该框架只支持注解开发，并完全是直接替你封装好返回对象，一切为了降低使用门槛, DLUT为我的大学简称,我是大连理工大学大三的一名学生
该框架与我的大学无关，但我的母校以及校友们对该源码以及其他文件有一切使用权利
为什么不推荐DLUT_DBUtil呢，如果你是商业开发，我非常不赞同你使用该框架，即便是该框架具有极其轻量型的特点，你需要的是一个有完善维护团队的框架，
为什么要使用DLUT_DBUtil呢，因为它很简单，你可以极易上手完成你的demo项目,如果你希望研究mybatis源码，却被繁重的代码带的晕乎转向
这款轻量级的是你研究如何实现一个持久层框架的一个很好的选择


框架特性:
1.只需要配置好连接数据库的信息
2.再编写查询的接口并加上注解并编写sql语句
3.完成数据库操作


使用方式:

1.两种使用方式，一种是从src中获取我的源码，自己编译，该项目是idea下的mavne项目
如果你使用这种使用方式，可以将.idea src target DLUT_DBUtil.iml pom.xml以外的所有文件忽略

2.另一种是直接导入jar包使用，所有你需要使用的jar包都在jars中，前提是，里面的连接器是mysql-connetor8.0，
所以你要确保两点，一点是你是mysql数据库，另一个是你的数据库可以使用该版本的连接器，
如果你是别的数据库或者连接器不适配，替换成你适配的jar包就可以


注意 强调几个点
1. 你所编写的dao实体类的属性名称必须与数据库一一对应
2. 你编写的接口方法上，只能有框架提供注解的一个 比如不能再接口注明方法上添加 @Select @Insert @UpDate @Delete 一个以上接口
3. 源码中有cn.dlut.edu.example包，里面有示例方法，基本上包含框架的全部功能，不要尝试从别的内部类中去获取信息



下面展示如何使用

第一步:
编写 jdbc.property文件(当然可以是别的名称),或者你编写一个Property类也是支持的,下面展示第一种编写文件的方式

driverClassName=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/mydata?useSSL=false&serverTimezone=UTC
username=era
password=123456


第二步:
编写实体类 设置get set方法

public class Student {
    private int id;
    private String name;
    private String phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}


第三步,编写查询接口:
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


第四步:展示几个查询实例

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

// 查询示例代码

public class SelectTest {
    public static void main(String[] args) {
        // 利用classLoader从类路径下以流的方式读取jdbc.property文件
        InputStream resourceAsStream = SelectTest.class.getClassLoader().getResourceAsStream("jdbc.property");
        // 构建一个DataBaseAccessBeanFactory对象
        DataBaseAccessBeanFactory factory = new DataBaseAccessBeanFactory().build(resourceAsStream);
        StudentMapper proxyObject = (StudentMapper) factory.getProxyObject(StudentMapper.class);
        List<Student> allStudent = proxyObject.getAllStudent();
        for (Student student : allStudent) {
            System.out.println(student);
        }
        System.out.println("-------------------------------------------");

        Student oneStudent = proxyObject.getOneStudent(1);
        System.out.println(oneStudent);
    }
}

结果：
Student{id=1, name='era', phone='12315'}
Student{id=2, name='fa', phone='56561232165'}
Student{id=3, name='bowen', phone='15524869136'}
-------------------------------------------
Student{id=1, name='era', phone='12315'}




package cn.dlut.edu.example;

import cn.dlut.edu.boot.DataBaseAccessBeanFactory;
import cn.dlut.edu.example.entry.Student;
import cn.dlut.edu.example.myInterface.StudentMapper;

import java.io.InputStream;


/*
    这是用于展示该框架如何使用的代码，您的数据库中表的类型肯定不同，
    如果想复用我这套代码，请在数据库中创建 mydata数据库
    再创建 student 表
    表格式为

    int     String      String
    id      name        phone
 */

// 更新示例代码

public class UpdateTest {
    public static void main(String[] args) {
        // 利用classLoader从类路径下以流的方式读取jdbc.property文件
        InputStream resourceAsStream = UpdateTest.class.getClassLoader().getResourceAsStream("jdbc.property");
        // 构建一个DataBaseAccessBeanFactory对象
        DataBaseAccessBeanFactory factory = new DataBaseAccessBeanFactory().build(resourceAsStream);
        StudentMapper proxyObject = (StudentMapper) factory.getProxyObject(StudentMapper.class);
        proxyObject.updateStudent("12315", 1);
        Student oneStudent = proxyObject.getOneStudent(1);
        System.out.println(oneStudent);
    }
｝


结果:Student{id=1, name='era', phone='12315'}


第五步:我的联系方式  1295289600@qq.com 
