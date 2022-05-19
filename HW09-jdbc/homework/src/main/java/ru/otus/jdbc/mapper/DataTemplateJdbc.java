package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.exceptions.MapperException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(
                connection,
                entitySQLMetaData.getSelectByIdSql(),
                List.of(id),
                this::map
        );
    }

    @Override
    public List<T> findAll(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(entitySQLMetaData.getSelectAllSql())) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapList(resultSet);
        } catch (SQLException e) {
            throw new MapperException(e);
        }
    }

    @Override
    public long insert(Connection connection, T client) {
        List<Object> insertParams = getObjectParams(client);
        return dbExecutor.executeStatement(
                connection,
                entitySQLMetaData.getInsertSql(),
                insertParams
        );
    }

    @Override
    public void update(Connection connection, T client) {
        List<Object> insertParams = getObjectParams(client);
        Field idField = entityClassMetaData.getIdField();
        try {
            idField.setAccessible(true);
            insertParams.add(idField.get(client));
        } catch (IllegalAccessException e) {
            throw new MapperException(e);
        }
        dbExecutor.executeStatement(
                connection,
                entitySQLMetaData.getInsertSql(),
                insertParams
        );
    }

    private T map(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                T newObject = entityClassMetaData.getConstructor().newInstance();
                entityClassMetaData.getAllFields().forEach(field -> {
                    field.setAccessible(true);
                    try {
                        field.set(newObject, resultSet.getObject(field.getName(), field.getType()));
                    } catch (IllegalAccessException | SQLException e) {
                        throw new MapperException(e);
                    }
                });
                return newObject;
            } else {
                return null;
            }
        } catch (SQLException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new MapperException(e);
        }
    }

    private List<T> mapList(ResultSet resultSet) {
        List<T> resultList = new ArrayList<>();
        while (true) {
            try {
                if (!resultSet.next()) break;
                resultList.add(map(resultSet));
            } catch (SQLException e) {
                throw new MapperException(e);
            }
        }

        return resultList;
    }

    private List<Object> getObjectParams(T object) {
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        List<Object> parameters = new ArrayList<>();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                parameters.add(field.get(object));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return parameters;
    }
}
