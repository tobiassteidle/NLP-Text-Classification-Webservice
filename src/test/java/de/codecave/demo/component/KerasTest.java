package de.codecave.demo.component;

import de.codecave.demo.component.impl.KerasService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class KerasTest {

    private KerasService kerasService = new KerasService();

    @Test
    void tokenizer() throws Exception {

        final int[] seqence = kerasService.textToSequence("ferrell molly shannon cover royal wedding cord tish");
        Arrays.stream(seqence)
                .forEach(val -> System.out.println("- " + val));

        assertThat(seqence, is(new int[]{266, 754, 584}));
    }
}
