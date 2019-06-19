package com.follow.common.util;

import java.lang.reflect.Field;
import java.net.URLEncoder;

public class BeanUtil {

    /**
     * 对象转url参数
     * @param clazz
     * @return
     */
    public static String beanToUrlParam(Object clazz,boolean encode) {
        Field[] fields = clazz.getClass().getDeclaredFields();

        StringBuilder requestURL = new StringBuilder();
        try {
            boolean flag = true;
            String property, value;
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                // 允许访问私有变量
                field.setAccessible(true);

                // 属性名
                property = field.getName();
                // 属性值
                if (field.get(clazz) == null) {
                    continue;
                }
                value = field.get(clazz).toString();

                String params;
                if (encode) {
                    params = property + "=" + URLEncoder.encode(value, "UTF-8");
                } else {
                    params = property + "=" + value;
                }
                if (flag) {
                    requestURL.append(params);
                    flag = false;
                } else {
                    requestURL.append("&" + params);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestURL.toString();
    }
}
