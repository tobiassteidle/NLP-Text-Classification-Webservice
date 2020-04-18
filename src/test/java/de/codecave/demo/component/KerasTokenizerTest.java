package de.codecave.demo.component;

import de.codecave.demo.component.impl.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ContextConfiguration(classes = {
        KerasTokenizerImpl.class})
@ExtendWith(SpringExtension.class)
public class KerasTokenizerTest {

    @Autowired
    private TokenizerService kerasService;

    @Test
    void tokenizer() throws Exception {

        final int[] sequence = kerasService.textToSequence("tom hardi complet unrecogniz al capon new movi");

        assertThat(sequence, is(new int[]{454, 1073, 1376, 4, 139}));

    }

}
