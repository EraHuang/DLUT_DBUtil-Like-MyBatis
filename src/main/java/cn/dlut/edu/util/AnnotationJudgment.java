package cn.dlut.edu.util;

public class AnnotationJudgment {
    public static boolean hasDefinedAnnotation(String annotationType){
        if(annotationType.contains("Select") || annotationType.contains("Insert") ||
        annotationType.contains("Delete") || annotationType.contains("Update"))
            return true;
        return false;
    }
}
