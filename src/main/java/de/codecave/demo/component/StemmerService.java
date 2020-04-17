package de.codecave.demo.component;

import java.util.Optional;

public interface StemmerService {

    Optional<String> tryStemming(String word);

}
