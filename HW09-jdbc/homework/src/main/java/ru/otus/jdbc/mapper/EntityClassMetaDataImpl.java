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

    private final String className;
    private final Constructor<T> constructor;
    private final Field idField;
    private final List<Field> fields;

    public EntityClassMetaDataImpl(Class<T> type) {
        this.clazz = type;

        this.className = defineClassName(type);
        this.constructor = defineConstructor(type);
        this.idField = defineIdField(type);
        this.fields = defineAllClassFields(type);
    }

    private String defineClassName(Class<T> type) {
        return type.getSimpleName().toLowerCase();
    }

    public Constructor<T> defineConstructor(Class<T> type) {
        Constructor<T> constructor;
        try {
            constructor = type.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return constructor;
    }

    public Field defineIdField(Class<T> type) {
        for (Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }
        throw new MapperException("Entity class dose not have Id field");
    }

    private List<Field> defineAllClassFields(Class<T> type) {
        return Arrays.stream(type.getDeclaredFields()).toList();
    }

    @Override
    public String getName() {
        return this.className;
    }

    @Override
    public Constructor<T> getConstructor() {
        return this.constructor;
    }

    @Override
    public Field getIdField() {
        return this.idField;
    }

    @Override
    public List<Field> getAllFields() {
        return this.fields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        List<Field> resultList = new ArrayList<>();
        for (Field field : this.fields) {
            if (!field.getName().equals(this.idField.getName())) {
                resultList.add(field);
            }
        }
        return resultList;
    }
}
