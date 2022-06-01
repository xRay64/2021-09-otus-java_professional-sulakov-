package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {
    private final EntityClassMetaData<T> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        String resultSql;
        resultSql = "select " + getFieldsString(entityClassMetaData.getAllFields()) + " from " + getTableNameFromClassName();
        return resultSql;
    }

    @Override
    public String getSelectByIdSql() {
        String idFieldName = entityClassMetaData.getIdField().getName();
        return getSelectAllSql() + " where " + idFieldName + " = ?";
    }

    @Override
    public String getInsertSql() {
        List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        return "insert into " + getTableNameFromClassName() +
                " (" + getFieldsString(fieldsWithoutId) + ") " +
                "values (" +
                getAllFieldsInsertPlaceholdersString(fieldsWithoutId)
                + ")";
    }

    @Override
    public String getUpdateSql() {
        String idFieldName = entityClassMetaData.getIdField().getName();
        return "update " + getTableNameFromClassName() +
                 " set " + getAllFieldsUpdatePlaceholderString() +
               " where " + idFieldName + " = ?";
    }

    private String getTableNameFromClassName() {
        return entityClassMetaData.getName();
    }

    private String getFieldsString(List<Field> fields) {
        String fieldsString = fields.stream().map(o -> o.getName() + ", ").collect(Collectors.joining());
        fieldsString = fieldsString.substring(0, fieldsString.length() - 2);
        return fieldsString;
    }

    private String getAllFieldsInsertPlaceholdersString(List<Field> fields) {
        StringBuilder result = new StringBuilder();
        result.append("?, ".repeat(fields.size()));
        result = new StringBuilder(result.substring(0, result.length() - 2));
        return result.toString();
    }

    private String getAllFieldsUpdatePlaceholderString() {
        StringBuilder result = new StringBuilder();
        List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        for (Field field : fieldsWithoutId) {
            result.append(field.getName()).append(" = ?, ");
        }
        result = new StringBuilder(result.substring(0, result.length() - 2));
        return result.toString();
    }
}
