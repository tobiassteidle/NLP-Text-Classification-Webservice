package de.codecave.demo.component;


import java.util.Optional;

public interface LemmatizerService {
    String lemmatize(String word);

    Optional<String> tryLemmatize(String word);
}
