package cn.dlut.edu.util;

import org.junit.jupiter.api.Test;

public class MethodBuilder {
    public static String setMethodBuilder(String attributes){
        String s = attributes.toLowerCase();
        String method = "set".concat(s.substring(0, 1).toUpperCase() + s.substring(1, s.length()));
        return method;
    }
}
