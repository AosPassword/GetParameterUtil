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
        Enumeration<String>  enumeration=req.getParameterNames();
        Object object=aClass.newInstance();
        try {
            while (enumeration.hasMoreElements()) {
                String paramName = enumeration.nextElement();
                String paramValue = req.getParameter(paramName);
                Field field = aClass.getDeclaredField(paramName);
                String field_type=field.getType().getSimpleName();
                System.out.println(field.getType());
                System.out.println(initcap(paramName));
                Method method=aClass.getDeclaredMethod("set"+initcap(paramName),field.getType());
                if (field_type.equalsIgnoreCase("String")){
                    method.invoke(object,paramValue);
                }else if ("integer".equalsIgnoreCase(field_type)||"int".equalsIgnoreCase(field_type)){
                    method.invoke(object,Integer.parseInt(paramValue));
                }else if ("double".equalsIgnoreCase(field_type)){
                    method.invoke(object,Double.parseDouble(paramValue));
                }else if ("data".equalsIgnoreCase(field_type)){
                    method.invoke(object,new SimpleDateFormat("yyyy-MM-dd").parse(paramValue));
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return object;
    }
    public static String initcap(String str){
        return str.substring(0,1).toUpperCase().concat(str.substring(1).toLowerCase());
    }
}
