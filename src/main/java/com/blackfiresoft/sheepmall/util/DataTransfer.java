package com.blackfiresoft.sheepmall.util;

import com.blackfiresoft.sheepmall.exception.CustomException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 提供实体和DTO数据转换的工具类
 */
@Slf4j
public class DataTransfer {

    public static <T> T transfer(Object target, Object... sources) throws CustomException {
        if (target == null || sources == null || sources.length == 0) {
            throw new CustomException("target or sources is null or empty");
        }
        Class<?> targetClass = target.getClass();
        Map<String, Field> targetFieldMap = new HashMap<>();
        if (targetClass.getSuperclass() != null) {
            targetFieldMap = getFieldWithSuperClassMap(targetClass);
        }else {
            targetFieldMap = getFieldMap(targetClass);
        }
        for (Object source : sources) {
            if (source == null) {
                continue;
            }
            Class<?> sourceClass = source.getClass();
            Map<String, Field> sourceFieldMap = new HashMap<>();
            if (sourceClass.getSuperclass() != null) {
                sourceFieldMap = getFieldWithSuperClassMap(sourceClass);
            }else {
                sourceFieldMap = getFieldMap(sourceClass);
            }
            for (Map.Entry<String, Field> entry : targetFieldMap.entrySet()) {
                Field targetField = entry.getValue();
                String fieldName = entry.getKey();
                Field sourceField = sourceFieldMap.get(fieldName);
                if (sourceField != null && targetField.getType().equals(sourceField.getType())) {
                    sourceField.setAccessible(true);
                    try {
                        Object value = sourceField.get(source);
                        if (value != null) {
                            targetField.set(target, value);
                        }
                    } catch (IllegalAccessException e) {
                        log.error("field access error, field name: {}", fieldName, e);
                        throw new CustomException("field access error");
                    }
                }
            }
        }
        return (T) target;
    }

    private static Map<String, Field> getFieldMap(Class<?> clazz) {
        Map<String, Field> fieldMap = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            fieldMap.put(field.getName(), field);
        }
        fieldMap.remove("serialVersionUID");
        return fieldMap;
    }

    private static Map<String, Field> getFieldWithSuperClassMap(Class<?> clazz) {
        Map<String, Field> fieldMap = getFieldMap(clazz);
        for (Field superField : clazz.getSuperclass().getDeclaredFields()) {
            superField.setAccessible(true);
            fieldMap.put(superField.getName(), superField);
        }
        fieldMap.remove("serialVersionUID");
        return fieldMap;
    }
}
