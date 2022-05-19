package ru.otus.jdbc.mapper;

import ru.otus.annotation.Id;
import ru.otus.exceptions.MapperException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final Class<T> clazz;

    public EntityClassMetaDataImpl(Class<T> type) {
        this.clazz = type;
    }

    @Override
    public String getName() {
        return clazz.getSimpleName().toLowerCase();
    }

    @Override
    public Constructor<T> getConstructor() {
        Constructor<T> constructor;
        try {
            constructor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return constructor;
    }

    @Override
    public Field getIdField() {
        Field resultField = null;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }
        throw new MapperException("Entity class dose not have Id field");
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.stream(clazz.getDeclaredFields()).toList();
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        List<Field> resultList = new ArrayList<>();
        Field idField = getIdField();
        if (idField == null) {
            throw new MapperException("Entity class dose not have Id field");
        }
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.getName().equals(idField.getName())) {
                resultList.add(field);
            }
        }
        return resultList;
    }
}
