import org.example.StringCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void testMultipleNumbers() {
        assertEquals(6, StringCalculator.add("1,2,3"));
    }

    @Test
    void testNewlineAsDelimiter() {
        assertEquals(6, StringCalculator.add("1\n2,3"));
    }

    @Test
    void testCustomDelimiter() {
        assertEquals(3, StringCalculator.add("//;\n1;2"));
    }

    @Test
    void testNegativeNumbersThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> StringCalculator.add("1,-2,3,-4"));
        assertEquals("negatives not allowed: -2, -4", exception.getMessage());
    }

    @Test
    void testIgnoreNumbersGreaterThan1000() {
        assertEquals(2, StringCalculator.add("2,1001"));
        assertEquals(6, StringCalculator.add("1001,2,4"));
        assertEquals(0, StringCalculator.add("1001,1002"));
    }

    @Test
    void testCustomDelimiterWithMultipleChars() {
        assertEquals(6, StringCalculator.add("//[***]\n1***2***3"));
    }

    @Test
    void testMultipleCustomDelimiters() {
        assertEquals(6, StringCalculator.add("//[*][%]\n1*2%3"));
    }

    @Test
    void testCustomDelimiterWithSpecialCharacters() {
        assertEquals(6, StringCalculator.add("//[$&]\n1$&2$&3"));
    }

    @Test
    void testDelimitersWithSpecialCharacters() {
        assertEquals(6, StringCalculator.add("//[;][*]\n1;2*3"));
    }

    @Test
    void testEmptyCustomDelimiter() {
        assertEquals(6, StringCalculator.add("//\n1,2,3"));
    }



}
