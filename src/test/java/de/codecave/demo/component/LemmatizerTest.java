package de.codecave.demo.component;

import de.codecave.demo.component.impl.LemmatizerCoreNLPServiceImpl;
import edu.stanford.nlp.simple.Sentence;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LemmatizerTest {

    private LemmatizerService lemmatizerService = new LemmatizerCoreNLPServiceImpl();

    @Test
    void lemm_mistakes() {

        final Sentence sentence = new Sentence("7 Fashion Mistakes You\'ll Regret Forever");

        assertThat(sentence.lemma(2), is("mistake"));

    }

    @Test
    void use_corenlp() {
        assertThat(lemmatizerService.lemmatize("Mistakes"), is("mistake"));
    }

    @Test
    void caring() {
        assertThat(lemmatizerService.lemmatize("Caring"), is("care"));
    }

    @Test
    void cannot_lemmatize() {
        try {
            lemmatizerService.lemmatize("#num#");
            Assert.fail("exception expected");
        } catch (IllegalStateException ise) {
            assertThat(ise.getMessage(), is("Must provide single token but was <#num#>"));
        }
    }

    @Test
    void fail_on_multiple_tokens() {
        Assertions.assertThrows(IllegalStateException.class,
                () -> lemmatizerService.lemmatize("Fashion Mistakes"));
    }

}
