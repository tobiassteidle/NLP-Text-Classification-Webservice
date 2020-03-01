package de.codecave.demo.component;

import de.codecave.demo.component.impl.KerasServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class KerasTokenizerTest {

    private TokenizerService kerasService = new KerasServiceImpl();

    @Test
    void tokenizer() throws Exception {

        final int[] sequence = kerasService.textToSequence("ferrell molly shannon cover royal wedding cord tish");

        assertThat(sequence, is(new int[]{266, 754, 584}));
    }

}
