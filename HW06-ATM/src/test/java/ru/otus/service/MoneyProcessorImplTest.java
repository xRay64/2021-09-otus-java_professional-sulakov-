package ru.otus.service;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import ru.otus.ext.RubleBanknotes;
import ru.otus.service.exception.NotEnoughBanknotesException;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@Description("MoneyProcessorImplTest должен")
class MoneyProcessorImplTest {
    private static final Map<Integer, Integer> BUNCH_OF_MONEY_TO_INSERT = Map.of(1000, 1, 500, 2, 100, 3);
    private static final int TEST_MONEY_AMOUNT = 3750;
    private static final int WRONG_TEST_MONEY_AMOUNT = 3760;

    @Test
    @Description("отдавать единственный экземпляр себя(быть синглтоном)")
    void shouldBeSingleton() {
        MoneyProcessor moneyProcessor = MoneyProcessorImpl.get();
        assertThat(moneyProcessor).isNotNull();
        MoneyProcessor moneyProcessor1 = MoneyProcessorImpl.get();
        assertThat(moneyProcessor1)
                .isNotNull()
                .isEqualTo(moneyProcessor);
    }

    @Test
    @Description("зачислять деньги")
    void shouldPutMoney() {
        Cassette cassette = new CassetteImpl(new RubleBanknotes());
        MoneyProcessor moneyProcessor = MoneyProcessorImpl.get();
        moneyProcessor.putMoney(cassette, BUNCH_OF_MONEY_TO_INSERT);

        assertThat(cassette.getMapOfContent().get(1000))
                .isEqualTo(51);
        assertThat(cassette.getMapOfContent().get(500))
                .isEqualTo(52);
        assertThat(cassette.getMapOfContent().get(100))
                .isEqualTo(53);
    }

    @Test
    @Description("должен выдавать деньги")
    void shouldReturnMoney() {
        Cassette cassette = new CassetteImpl(new RubleBanknotes());
        MoneyProcessor moneyProcessor = MoneyProcessorImpl.get();
        try {
            moneyProcessor.getMoney(cassette, TEST_MONEY_AMOUNT);
        } catch (NotEnoughBanknotesException e) {
            e.printStackTrace();
        }

        assertThat(cassette.getMapOfContent().get(2000))
                .isEqualTo(49);
        assertThat(cassette.getMapOfContent().get(1000))
                .isEqualTo(49);
        assertThat(cassette.getMapOfContent().get(500))
                .isEqualTo(49);
        assertThat(cassette.getMapOfContent().get(200))
                .isEqualTo(49);
        assertThat(cassette.getMapOfContent().get(50))
                .isEqualTo(49);
    }

    @Test
    @Description("должен бросать исключение, если запрашиваемая сумма не может быть выдана")
    void shouldThrowExceptionOnWrongAmount() {
        Cassette cassette = new CassetteImpl(new RubleBanknotes());
        MoneyProcessor moneyProcessor = MoneyProcessorImpl.get();
        assertThatExceptionOfType(NotEnoughBanknotesException.class)
                .isThrownBy(() -> moneyProcessor.getMoney(cassette, WRONG_TEST_MONEY_AMOUNT))
                .withMessage("Not enough banknotes in cassette for requested operation");
    }
}