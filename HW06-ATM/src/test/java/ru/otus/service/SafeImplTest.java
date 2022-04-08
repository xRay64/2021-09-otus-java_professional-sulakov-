package ru.otus.service;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import ru.otus.ext.Banknote;
import ru.otus.ext.RubleBanknote;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Description("SafeImplTest должен")
class SafeImplTest {

    @Test
    @Description("отдавать корректную карту наполнения ячеек")
    void shouldReturnCorrectMapOfContent() {
        Safe safe = new SafeImpl("rubel");
        Map<Banknote, Integer> contentMap = safe.getCassettesContent();

        assertThat(contentMap.get(RubleBanknote.TWO_THOUSAND))
                .isEqualTo(50);
        assertThat(contentMap.get(RubleBanknote.THOUSAND))
                .isEqualTo(50);
        assertThat(contentMap.get(RubleBanknote.FIVE_HUNDRED))
                .isEqualTo(50);
    }

    @Test
    @Description("должен брать банкноты")
    void shouldGetBanknotes() {
        Safe safe = new SafeImpl("rubel");
        safe.getBanknotes(RubleBanknote.THOUSAND, 10);

        assertThat(safe.getCassettesContent().get(RubleBanknote.THOUSAND))
                .isEqualTo(40);
    }

    @Test
    @Description("должен ложить банкноты")
    void shouldPutBanknotes() {
        Safe safe = new SafeImpl("rubel");
        safe.putBanknotes(RubleBanknote.THOUSAND, 10);

        assertThat(safe.getCassettesContent().get(RubleBanknote.THOUSAND))
                .isEqualTo(60);
    }
}