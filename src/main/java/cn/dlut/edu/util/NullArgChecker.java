package cn.dlut.edu.util;

public class NullArgChecker {
    public static void check(Object arg, String message){
        if(arg == null)
            throw new IllegalArgumentException(message);
    }
}
