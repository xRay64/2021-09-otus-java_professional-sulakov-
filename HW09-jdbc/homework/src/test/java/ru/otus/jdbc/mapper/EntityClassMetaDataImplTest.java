package ru.otus.jdbc.mapper;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class EntityClassMetaDataImplTest {

    private final EntityClassMetaDataImpl<EntytyClassMetadateTestClass> entityClassMetaData =
            new EntityClassMetaDataImpl<>(EntytyClassMetadateTestClass.class);

    @Test
    void getName() {
        assertThat(entityClassMetaData.getName())
                .isEqualTo(EntytyClassMetadateTestClass.class.getSimpleName().toLowerCase());
    }

    @Test
    void getConstructor() throws NoSuchMethodException {
        assertThat(entityClassMetaData.getConstructor())
                .isEqualTo(EntytyClassMetadateTestClass.class.getConstructor());
    }

    @Test
    void getIdField() throws NoSuchFieldException {
        Class<EntytyClassMetadateTestClass> testClazz = EntytyClassMetadateTestClass.class;
        Field noField = testClazz.getDeclaredField("no");

        assertThat(entityClassMetaData.getIdField())
                .isEqualTo(noField);
    }

    @Test
    void getAllFields() {
        assertThat(entityClassMetaData.getAllFields())
                .containsAll(Arrays.asList(EntytyClassMetadateTestClass.class.getDeclaredFields()));
    }

    @Test
    void getFieldsWithoutId() throws NoSuchFieldException {
        List<Field> fieldsWithoutId = new ArrayList<>();
        Class<EntytyClassMetadateTestClass> testClazz = EntytyClassMetadateTestClass.class;
        fieldsWithoutId.add(testClazz.getDeclaredField("param1"));
        fieldsWithoutId.add(testClazz.getDeclaredField("param2"));

        assertThat(entityClassMetaData.getFieldsWithoutId())
                .containsAll(fieldsWithoutId);
    }
}