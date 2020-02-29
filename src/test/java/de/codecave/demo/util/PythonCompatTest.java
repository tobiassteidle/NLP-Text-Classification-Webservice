package de.codecave.demo.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static de.codecave.demo.util.Python3Compat.isnumeric;

public class PythonCompatTest {

    @Test
    void isnumeric_digits() {
        // https://stackoverflow.com/questions/228532/difference-between-char-isdigit-and-char-isnumber-in-c-sharp
        // https://stackoverflow.com/questions/24384852/difference-between-unicode-isdigit-and-unicode-isnumeric

        Assertions.assertEquals(false, isnumeric(""));
        Assertions.assertEquals(true, isnumeric("1"));
        Assertions.assertEquals(true, isnumeric("99"));
        Assertions.assertEquals(false, isnumeric("1.5"));
        Assertions.assertEquals(false, isnumeric("1,5"));
    }

    @Test
    void isnumeric_vulgar_fraction_one_tenth() {
        Assertions.assertEquals(true, isnumeric("⅒"));
    }

    @Test
    void isnumeric_roman() {
        Assertions.assertEquals(false, isnumeric("V"));
    }

}
