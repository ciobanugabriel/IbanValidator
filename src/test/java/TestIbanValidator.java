import org.example.IbanValidator;
import org.example.InvalidIbanException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestIbanValidator  {
    public final IbanValidator ibanValidator = new IbanValidator();
    @Test
    public void testTrueIbanValidator() throws Exception {

        assertTrue(ibanValidator.isIbanValid ("GB33BUK B20201555555555"));
    }
    @Test
    public void testFalseIbanValidator() throws Exception {

        assertFalse(ibanValidator.isIbanValid ("GB33BUK B22201555555555"));
    }
    @Test
    @Disabled
    public void testFalseIbanGoodNumberOfDigitAndZeroRemainderValidator() throws Exception {

        assertFalse(ibanValidator.isIbanValid ("FI06 9700 9700 0097 97"));
    }
    @Test
    public void testInvalidIbanValidator()  {

        Exception exception = assertThrows(InvalidIbanException.class,
                () -> ibanValidator.isIbanValid("GB33BUK B3202555555"));


        assertEquals(exception.getMessage(),"Wrong Iban! Length does not match.");
    }

    @Test
    public void testInvalidNumberCharValidator() {

        Exception exception = assertThrows(InvalidIbanException.class,
                () -> ibanValidator.isIbanValid("A"));

        assertEquals(exception.getMessage(),"Wrong Iban!");
    }
    @Test
    public void testInvalidCodeCountryValidator() {

        Exception exception = assertThrows(InvalidIbanException.class,
                () -> ibanValidator.isIbanValid("-1"));

        assertEquals(exception.getMessage(),"Wrong Iban! Code Country does not exist.");

    }
    @Test
    public void testNullIbanValidator() {

        Exception exception = assertThrows(InvalidIbanException.class,
                () -> ibanValidator.isIbanValid(""));

        assertEquals(exception.getMessage(),"Wrong Iban!");
    }
    @Test
    public void testInvalidIbanContainSpecialChar() {

        Exception exception = assertThrows(InvalidIbanException.class,
                () -> ibanValidator.isIbanValid("DE755121/8001245126199"));

        assertEquals(exception.getMessage(),"Wrong Iban! Contain special character.");
    }

    @Test
    public void testFileCountryCodeNameValidator()  {
        ibanValidator.setFileName("car.txt");

        Exception exception = assertThrows(InvalidIbanException.class,
                    () -> ibanValidator.isIbanValid("GB33BUK B20201555555555"));

        assertEquals(exception.getMessage(),"File not found!");

        ibanValidator.setFileName("date.txt");
    }

    @Test
    public void testInvalidIbanTooManyCharValidator()  {

        Exception exception = assertThrows(InvalidIbanException.class,
                () -> ibanValidator.isIbanValid("GB33BUK B3202555555314125151353523523654756867867867566456464645645645"));

        assertEquals(exception.getMessage(),"Wrong Iban! Length does not match.");
    }

}

