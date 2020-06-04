package cz.anona.snyverse.units;

import cz.anona.snyverse.services.RegexService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class RegexTest {

    @ParameterizedTest
    @ValueSource(strings = {"ahojAHOJ58#", "56D4sd6f4*", "jkUI5@d6"})
    public void testPasswordPossible(String password) {
        Assertions.assertTrue(RegexService.checkPassword(password), password);
    }

    @ParameterizedTest
    @ValueSource(strings = {"heslo", "654*", "sdfsdfsdf"})
    public void testPasswordImpossible(String password) {
        Assertions.assertFalse(RegexService.checkPassword(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"frantisekzavazal@seznam.cz", "kuku244@seznam.zc", "a@b.cz"})
    public void testEmailPossible(String email) {
        Assertions.assertTrue(RegexService.checkEmail(email), email);
    }

    @ParameterizedTest
    @ValueSource(strings = {"heslo", "654*", "sdfsdfsdf", "email@dsjfhi"})
    public void testEmailImpossible(String email) {
        Assertions.assertFalse(RegexService.checkEmail(email), email);
    }

}
