package de.codecave.demo.component.impl;

import de.codecave.demo.component.StemmerService;
import org.springframework.stereotype.Service;
import org.tartarus.snowball.ext.EnglishStemmer;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class StemmerServiceImpl implements StemmerService {

    private EnglishStemmer stemmer;

    @PostConstruct
    public void initialize() {
        this.stemmer = new EnglishStemmer();
    }

    @Override
    public Optional<String> tryStemming(String word) {
        this.stemmer.setCurrent(word);
        this.stemmer.stem();
        return Optional.of(this.stemmer.getCurrent());
    }

}
