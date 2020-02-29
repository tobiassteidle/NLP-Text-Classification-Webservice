package de.codecave.demo.component;

import de.codecave.demo.component.impl.LemmatizerCoreNLP;
import edu.stanford.nlp.simple.Sentence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LemmatizerTest {

    @Test
    void lemm_mistakes() {

        // ‘Caring’ -> Lemmatization -> ‘Care’
        final Sentence sentence = new Sentence("7 Fashion Mistakes You\'ll Regret Forever");

        Assertions.assertEquals("mistake", sentence.lemma(2));

    }

    @Test
    void use_corenlp() {
        Assertions.assertEquals("mistake", LemmatizerCoreNLP.lemmatize("Mistakes"));
    }

    @Test
    void cannot_lemmatize() {
        Assertions.assertEquals("mistake", LemmatizerCoreNLP.lemmatize("#num#"));
    }

    @Test
    void fail_on_multiple_tokens() {
        Assertions.assertThrows(IllegalStateException.class,
                () -> LemmatizerCoreNLP.lemmatize("Fashion Mistakes"));
    }

}
