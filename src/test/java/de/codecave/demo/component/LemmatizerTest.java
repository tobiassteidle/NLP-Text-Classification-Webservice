package de.codecave.demo.component;

import de.codecave.demo.component.impl.LemmatizerCoreNLPServiceImpl;
import edu.stanford.nlp.simple.Sentence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LemmatizerTest {

    private LemmatizerService lemmatizerService = new LemmatizerCoreNLPServiceImpl();

    @Test
    void lemm_mistakes() {

        // ‘Caring’ -> Lemmatization -> ‘Care’
        final Sentence sentence = new Sentence("7 Fashion Mistakes You\'ll Regret Forever");

        assertThat(sentence.lemma(2), is("mistake"));

    }

    @Test
    void use_corenlp() {
        assertThat(lemmatizerService.lemmatize("Mistakes"), is("mistake"));
    }

    @Test
    void cannot_lemmatize() {
        assertThat(lemmatizerService.lemmatize("#num#"), is("mistake"));
    }

    @Test
    void fail_on_multiple_tokens() {
        Assertions.assertThrows(IllegalStateException.class,
                () -> lemmatizerService.lemmatize("Fashion Mistakes"));
    }

}
