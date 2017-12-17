package com.liubowen.socketiomahjong.util.been;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author liubowen
 * @date 2017/12/16 23:03
 * @description
 */
public class ApplicationBeenContext {

    private static Map<Class<?>, Object> BEAN_CLASS_MAP = Maps.newHashMap();

    private static Map<String, Object> BEAN_NAME_MAP = Maps.newHashMap();

    public static void registBean(Object bean) {
        //  通过类型区别
        registBean(bean.getClass(), bean);
        //  通过名字区别
        registBean(bean.getClass().getSimpleName(), bean);
    }

    private static void registBean(Class<?> clazz, Object bean) {
        BEAN_CLASS_MAP.put(clazz, bean);
    }

    private static void registBean(String name, Object bean) {//通过名字区别
        BEAN_NAME_MAP.put(name, bean);
    }

    public static <T> T fetchBean(String name) {
        return (T) BEAN_NAME_MAP.get(name);
    }

    public static <T> T fetchBean(Class<T> clazz) {
        return (T) BEAN_CLASS_MAP.get(clazz);
    }

}
