package de.codecave.demo.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static de.codecave.demo.util.Python3Compat.isnumeric;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class Python3CompatTest {

    @Test
    void isnumeric_digits() {
        // https://stackoverflow.com/questions/228532/difference-between-char-isdigit-and-char-isnumber-in-c-sharp
        // https://stackoverflow.com/questions/24384852/difference-between-unicode-isdigit-and-unicode-isnumeric

        assertThat(isnumeric(""), is(false));
        assertThat(isnumeric("1"), is(true));
        assertThat(isnumeric("99"), is(true));
        assertThat(isnumeric("1.5"), is(false));
        assertThat(isnumeric("1,5"), is(false));
    }

    @Test
    void isnumeric_vulgar_fraction_one_tenth() {
        assertThat(isnumeric("⅒"), is(true));
    }

    @Test
    void isnumeric_roman() {
        assertThat(isnumeric("V"), is(false));
    }

}
