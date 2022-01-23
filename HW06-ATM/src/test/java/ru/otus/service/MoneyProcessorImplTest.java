package ru.otus.service;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import ru.otus.ext.Banknote;
import ru.otus.ext.RubleBanknote;
import ru.otus.service.exception.NotEnoughBanknotesException;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@Description("MoneyProcessorImplTest должен")
class MoneyProcessorImplTest {
    private static final Map<Banknote, Integer> BUNCH_OF_MONEY_TO_INSERT = Map.of(RubleBanknote.THOUSAND, 1, RubleBanknote.FIVE_HUNDRED, 2, RubleBanknote.HUNDRED, 3);
    private static final int TEST_MONEY_AMOUNT = 3750;
    private static final int WRONG_TEST_MONEY_AMOUNT = 3760;

    @Test
    @Description("зачислять деньги")
    void shouldPutMoney() {
        Safe safe = new SafeImpl("rubel");
        MoneyProcessor moneyProcessor = new MoneyProcessorImpl();
        moneyProcessor.putMoney(safe, BUNCH_OF_MONEY_TO_INSERT);

        assertThat(safe.getCassettesContent().get(RubleBanknote.THOUSAND))
                .isEqualTo(51);
        assertThat(safe.getCassettesContent().get(RubleBanknote.FIVE_HUNDRED))
                .isEqualTo(52);
        assertThat(safe.getCassettesContent().get(RubleBanknote.HUNDRED))
                .isEqualTo(53);
    }

    @Test
    @Description("должен выдавать деньги")
    void shouldReturnMoney() {
        Safe safe = new SafeImpl("rubel");
        MoneyProcessor moneyProcessor = new MoneyProcessorImpl();
        try {
            moneyProcessor.getMoney(safe, TEST_MONEY_AMOUNT);
        } catch (NotEnoughBanknotesException e) {
            e.printStackTrace();
        }

        assertThat(safe.getCassettesContent().get(RubleBanknote.TWO_THOUSAND))
                .isEqualTo(49);
        assertThat(safe.getCassettesContent().get(RubleBanknote.THOUSAND))
                .isEqualTo(49);
        assertThat(safe.getCassettesContent().get(RubleBanknote.FIVE_HUNDRED))
                .isEqualTo(49);
        assertThat(safe.getCassettesContent().get(RubleBanknote.TWO_HUNDRED))
                .isEqualTo(49);
        assertThat(safe.getCassettesContent().get(RubleBanknote.FIFTY))
                .isEqualTo(49);
    }

    @Test
    @Description("должен бросать исключение, если запрашиваемая сумма не может быть выдана")
    void shouldThrowExceptionOnWrongAmount() {
        Safe safe = new SafeImpl("rubel");
        MoneyProcessor moneyProcessor = new MoneyProcessorImpl();
        assertThatExceptionOfType(NotEnoughBanknotesException.class)
                .isThrownBy(() -> moneyProcessor.getMoney(safe, WRONG_TEST_MONEY_AMOUNT))
                .withMessage("Not enough banknotes in cassette for requested operation");
    }
}