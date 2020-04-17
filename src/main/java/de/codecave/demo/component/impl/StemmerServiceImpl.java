package de.codecave.demo.component.impl;

import de.codecave.demo.component.StemmerService;
import org.springframework.stereotype.Service;
import org.tartarus.snowball.ext.EnglishStemmer;

import java.util.Optional;

@Service
public class StemmerServiceImpl implements StemmerService {

    @Override
    public Optional<String> tryStemming(String word) {
        final EnglishStemmer stemmer = new EnglishStemmer();
        stemmer.setCurrent(word);
        stemmer.stem();
        return Optional.of(stemmer.getCurrent());
    }

}
