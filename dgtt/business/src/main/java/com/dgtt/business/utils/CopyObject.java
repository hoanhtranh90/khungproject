/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgtt.business.utils;

import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author abc
 */
public class CopyObject {

    public static Map<String, Field> getFields(Object o) {
        Map<String, Field> fs = new HashMap<>();
        for (Field field : o.getClass().getDeclaredFields()) {

            field.setAccessible(true);

            LabelAnotation column = field.getAnnotation(LabelAnotation.class);
            String name = field.getName();
            if (column != null) {
                if (column.ignored()) {
                    continue;
                }
                if (!column.name().isEmpty()) {
                    name = column.name();
                }
            }
            fs.put(name, field);
        }
        return fs;
    }

    public static void copyV(Object obFrom, Object obTo) throws IllegalArgumentException, IllegalAccessException {
        Map<String, Field> fFrom = getFields(obFrom);
        Map<String, Field> fTo = getFields(obTo);
        for (Map.Entry<String, Field> entrySet : fFrom.entrySet()) {
            if (!ObjectUtils.isEmpty(entrySet.getKey())) {
                String key = entrySet.getKey();
                Field value = entrySet.getValue();
                if (fTo.containsKey(key)) {
                    Object vFrom = value.get(obFrom);
                    if (vFrom != null && vFrom != "") {
                        fTo.get(key).set(obTo, vFrom);
                    }
                }
            }
        }
    }

    public static Object copy(Object obFrom, Object obTo) throws IllegalArgumentException, IllegalAccessException {
        Object clone = obTo;
        Map<String, Field> fFrom = getFields(obFrom);
        Map<String, Field> fTo = getFields(clone);
        for (Map.Entry<String, Field> entrySet : fFrom.entrySet()) {
            String key = entrySet.getKey();
            Field value = entrySet.getValue();
            if (fTo.containsKey(key)) {
                Object vFrom = value.get(obFrom);
                if (vFrom != null && vFrom != "") {
                    fTo.get(key).set(clone, vFrom);
                }
            }
        }
        return clone;
    }

//    public static Object cloneObject(Object obj, Object abc){
//        try{
////            Object clone = obj.getClass().newInstance();   
//            Object clone = abc;
//
//            for (Field field : obj.getClass().getDeclaredFields()) {
//                field.setAccessible(true);
//                if(field.get(obj) == null || Modifier.isFinal(field.getModifiers())){
//                    continue;
//                }
//                if(field.getType().isPrimitive() || field.getType().equals(String.class)
//                        || field.getType().getSuperclass().equals(Number.class)
//                        || field.getType().equals(Boolean.class)){
//                    field.set(clone, field.get(obj));
//                }else{
//                    Object childObj = field.get(obj);
//                    if(childObj == obj){
//                        field.set(clone, clone);
//                    }
//                }
//            }
//            return clone;
//        }catch(Exception e){
//            return null;
//        }
//    }
}
