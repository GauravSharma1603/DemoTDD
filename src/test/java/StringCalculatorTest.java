import org.example.StringCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringCalculatorTest {

    @Test
    void testEmptyStringReturnsZero() {
        assertEquals(0, StringCalculator.add(""));
    }

    @Test
    void testSingleNumberReturnsValue() {
        assertEquals(1, StringCalculator.add("1"));
    }

    @Test
    void testTwoNumbersSeparatedByComma() {
        assertEquals(5, StringCalculator.add("3,2"));
    }
}
