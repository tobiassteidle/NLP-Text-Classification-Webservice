package de.codecave.demo.component.impl;

import com.google.common.base.Preconditions;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.simple.Token;

import java.util.List;

public final class LemmatizerCoreNLP {

    public static void selfcheck() {
        // naive startup
        final Sentence sentence = new Sentence("7 Fashion Mistakes You\'ll Regret Forever");
        sentence.lemma(2);
    }

    public static String lemmatize(String word) {
        final Sentence sentence = new Sentence(word);
        final List<Token> tokens = sentence.tokens();
        Preconditions.checkState(tokens.size() == 1, "Must provide single token but was <%s>", word);
        return sentence.lemma(0);
    }

}
