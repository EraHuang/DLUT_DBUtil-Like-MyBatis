package cn.dlut.edu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

// 用于插入的注解
// 该注解只用于方法上
public @interface Insert {
    String sql();
}
