import org.example.StringCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringCalculatorTest {

    @Test
    void testEmptyStringReturnsZero() {
        assertEquals(0, StringCalculator.add(""));
    }
}
