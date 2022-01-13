package ru.otus.service;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import ru.otus.ext.RubleBanknotes;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Description("CassetteImplTest должен")
class CassetteImplTest {

    @Test
    @Description("отдавать корректную карту наполнения ячеек")
    void shouldReturnCorrectMapOfContent() {
        Cassette cassette = new CassetteImpl(new RubleBanknotes());
        Map<Integer, Integer> contentMap = cassette.getMapOfContent();

        assertThat(contentMap.get(2000))
                .isEqualTo(50);
        assertThat(contentMap.get(1000))
                .isEqualTo(50);
        assertThat(contentMap.get(500))
                .isEqualTo(50);
    }

    @Test
    @Description("должен брать банкноты")
    void shouldGetBanknotes() {
        Cassette cassette = new CassetteImpl(new RubleBanknotes());
        cassette.getBanknotes(1000, 10);

        assertThat(cassette.getMapOfContent().get(1000))
                .isEqualTo(40);
    }

    @Test
    @Description("должен ложить банкноты")
    void shouldPutBanknotes() {
        Cassette cassette = new CassetteImpl(new RubleBanknotes());
        cassette.putBanknotes(1000, 10);

        assertThat(cassette.getMapOfContent().get(1000))
                .isEqualTo(60);
    }
}