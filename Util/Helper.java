package org.redrock.web.DBCPUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;

public class Helper {
    public static Object getData(Class<?> aClass,HttpServletRequest req) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        Object object=aClass.newInstance();
        Field[] fields=aClass.getFields();
        if (fields.length!=0) {
            try {
                for (Field field:fields) {
                    String paramName=field.getName();
                    String field_type=field.getType().getSimpleName();
                    String paramValue=req.getParameter(paramName);
                    field.setAccessible(true);
                    if (field_type.equalsIgnoreCase("String")) {
                        field.set(object, paramValue);
                    } else if ("integer".equalsIgnoreCase(field_type) || "int".equalsIgnoreCase(field_type)) {
                        field.set(object, Integer.parseInt(paramValue));
                    } else if ("double".equalsIgnoreCase(field_type)) {
                        field.set(object, Double.parseDouble(paramValue));
                    } else if ("date".equalsIgnoreCase(field_type)) {
                        field.set(object, new SimpleDateFormat("yyyy-MM-dd").parse(paramValue));
                    } else if ("long".equalsIgnoreCase(field_type)){
                        field.set(object,Long.parseLong(paramValue));
                    } else if ("float".equalsIgnoreCase(field_type)){
                        field.set(object,Float.parseFloat(paramValue));
                    } else if ("boolean".equalsIgnoreCase(field_type)){
                        field.set(object,Boolean.valueOf(paramValue));
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return object;
    }
}
