package cn.dlut.edu.example.entry;

/*
    这是用于展示该框架如何使用的代码，您的数据库中表的类型肯定不同，
    如果想复用我这套代码，请在数据库中创建 mydata数据库
    再创建 student 表
    表格式为

    int     String      String
    id      name        phone
 */

// 这是Dao类，字段与数据库一一对应
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

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
