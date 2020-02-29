package de.codecave.demo.component.impl;

import com.google.common.base.Preconditions;
import de.codecave.demo.component.LemmatizerService;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.simple.Token;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public final class LemmatizerCoreNLPServiceImpl implements LemmatizerService {

    public static void selfcheck() {
        // naive startup
        final Sentence sentence = new Sentence("7 Fashion Mistakes You\'ll Regret Forever");
        sentence.lemma(2);
    }

    @PostConstruct
    public void setup() {
        selfcheck();
    }

    @Override
    public String lemmatize(String word) {
        final Sentence sentence = new Sentence(word);
        final List<Token> tokens = sentence.tokens();
        Preconditions.checkState(tokens.size() == 1, "Must provide single token but was <%s>", word);
        return sentence.lemma(0);
    }

}
