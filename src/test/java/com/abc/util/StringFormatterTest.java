package com.abc.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.abc.util.StringFormatter.toDollars;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @project MyBank
 */
@DisplayName("Testing String formatter")
public class StringFormatterTest {

    @Nested
    @DisplayName("When calling pluralFormatter")
    class PluralFormatterTest{

        @Test
        @DisplayName("and passing string: word and int: 1, should output string: 1 word")
        public void testNonPlural(){
            String expected = "1 word";
            String actual = StringFormatter.pluralFormatter(1,"word");
            assertEquals(expected,actual);
        }

        @Test
        @DisplayName("and passing string: word and int: 5, should output string: 5 words")
        public void testPlural(){
            String expected = "5 words";
            String actual = StringFormatter.pluralFormatter(5,"word");
            assertEquals(expected,actual);
        }
    }

    @Nested
    @DisplayName("When calling toDollars")
    class ToDollarsTest{

        @Test
        @DisplayName("And passing double: 500, should output $500.00")
        public void testBasicConversionOne(){
            String expected = "$500.00";
            String actual = toDollars(500);

            assertEquals(expected,actual, () -> "Expected String: " + expected + " Actual: " + actual);
        }

        @Test
        @DisplayName("And passing double: 500.50, should output $500.50")
        public void testBasicConversionTwo(){
            String expected = "$500.50";
            String actual = toDollars(500.50);

            assertEquals(expected,actual, () -> "Expected String: " + expected + " Actual: " + actual);
        }

        @Test
        @DisplayName("And passing double: 500.995, should output $501.00")
        public void testRoundUpConversion(){
            String expected = "$501.00";
            String actual = toDollars(500.995);

            assertEquals(expected,actual, () -> "Expected String: " + expected + " Actual: " + actual);

        }

        @Test
        @DisplayName("And passing double: 500.994, should output $500.99")
        public void testRoundDownConversion(){
            String expected = "$500.99";
            String actual = toDollars(500.994);

            assertEquals(expected,actual, () -> "Expected String: " + expected + " Actual: " + actual);

        }
    }

}
