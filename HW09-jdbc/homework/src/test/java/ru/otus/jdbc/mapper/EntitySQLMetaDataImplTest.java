package ru.otus.jdbc.mapper;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EntitySQLMetaDataImplTest {

    private static final String SELECT_ALL_STRING = "select no, param1, param2 from entytyclassmetadatetestclass";
    private static final String SELECT_BI_ID_STRING = "select no, param1, param2 from entytyclassmetadatetestclass where no = ?";
    private static final String INSERT_STRING = "insert into entytyclassmetadatetestclass (param1, param2) values (?, ?)";
    private static final String UPDATE_STRING = "update entytyclassmetadatetestclass set param1 = ?, param2 = ? where no = ?";

    private final EntityClassMetaDataImpl<EntytyClassMetadateTestClass> entityClassMetaData =
            new EntityClassMetaDataImpl<>(EntytyClassMetadateTestClass.class);
    private final EntitySQLMetaDataImpl entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);

    @Test
    void getSelectAllSql() {
        assertThat(entitySQLMetaData.getSelectAllSql())
                .isEqualTo(SELECT_ALL_STRING);
    }

    @Test
    void getSelectByIdSql() {
        assertThat(entitySQLMetaData.getSelectByIdSql())
                .isEqualTo(SELECT_BI_ID_STRING);
    }

    @Test
    void getInsertSql() {
        System.out.println(entitySQLMetaData.getInsertSql());
        assertThat(entitySQLMetaData.getInsertSql())
                .isEqualTo(INSERT_STRING);
    }

    @Test
    void getUpdateSql() {
        System.out.println(entitySQLMetaData.getUpdateSql());
        assertThat(entitySQLMetaData.getUpdateSql())
                .isEqualTo(UPDATE_STRING);
    }
}