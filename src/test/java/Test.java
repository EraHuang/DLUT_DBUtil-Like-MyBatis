import cn.dlut.edu.annotation.Insert;
import cn.dlut.edu.annotation.Select;
import cn.dlut.edu.boot.DataBaseAccessBeanFactory;
import cn.dlut.edu.cache.DaoInterfaceDefinitionCache;
import cn.dlut.edu.conf.DaoInterfaceDefinition;
import cn.dlut.edu.conf.InterfaceMethodDefinition;
import cn.dlut.edu.conf.InterfaceRegister;
import cn.dlut.edu.entry.Student;
import cn.dlut.edu.proxy.TestInterface;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;

public class Test {
    @org.junit.Test
    public void test(){
        InterfaceRegister.register(TestInterface.class.getName(), TestInterface.class);
        DaoInterfaceDefinition daoInterfaceDefinition = DaoInterfaceDefinitionCache.get(TestInterface.class.getName());
        List<InterfaceMethodDefinition> methodList = daoInterfaceDefinition.getMethodList();
        for (InterfaceMethodDefinition interfaceMethodDefinition : methodList){
            Parameter[] parameters = interfaceMethodDefinition.getParameters();
            for (Parameter parameter : parameters) {
                System.out.print(parameter.getName() + "\t");
            }
            System.out.println();
        }
    }

    @org.junit.Test
    public void test2() throws Exception {
        Student student = Student.class.newInstance();
        Method method = Student.class.getMethod("setName", String.class);
        method.invoke(student, "era");
        System.out.println(student);
    }


    @org.junit.Test
    public void test3() throws NoSuchFieldException {
        Field id = Student.class.getDeclaredField("id");
        System.out.println(id.getType());
    }

    @org.junit.Test
    public void test4(){
        Object obj = new LinkedList<String>();
        System.out.println(List.class.isInstance(obj));
    }

    public static void main(String[] args) throws NoSuchFieldException {
        Field field = Student.class.getDeclaredField("id");
        Class<?> type = field.getType();
        System.out.println(type);
    }


}
