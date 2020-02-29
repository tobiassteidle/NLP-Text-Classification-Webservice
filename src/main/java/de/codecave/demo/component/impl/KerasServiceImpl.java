package de.codecave.demo.component.impl;

import com.google.common.base.Preconditions;
import de.codecave.demo.component.TokenizerService;
import org.apache.commons.lang3.ArrayUtils;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.preprocessing.text.KerasTokenizer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class KerasServiceImpl implements TokenizerService {

    public static final int MAX_FEATURES = 2000;

    private final KerasTokenizer kerasTokenizer;

    public KerasServiceImpl() {
        kerasTokenizer = loadAndConfigure();
    }

    private static KerasTokenizer loadAndConfigure() {
        try {
            final Path tmpFile = Files.createTempFile("keras_tokenizer_config", ".json");

            final InputStream is = KerasServiceImpl.class.getResourceAsStream("/nlp/tokenizer.json");
            Preconditions.checkNotNull(is, "Keras config not found in classpath");
            Files.copy(is, tmpFile, StandardCopyOption.REPLACE_EXISTING);
            final KerasTokenizer kerasTokenizer = KerasTokenizer.fromJson(tmpFile.toString());
            Files.delete(tmpFile);

            kerasTokenizer.setNumWords(MAX_FEATURES);
            kerasTokenizer.setSplit(" ");

            return kerasTokenizer;
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        } catch (InvalidKerasConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Der Tokenizer ist in Python wie folgt implementiert:
     * MAX_FEATURES = 2000
     * tokenizer = Tokenizer(num_words=MAX_FEATURES, split=' ')
     * eq. in Java ist der KerasTokenizer
     *
     static KerasTokenizer	fromJson(java.lang.String jsonFileName)
     Import Keras Tokenizer from JSON file created with `tokenizer.to_json()` in Python.

     return tokenizer.texts_to_sequences(text)
     */

    // Die tokenizer.json liegt unter resources/nlp/tokenizer.json
    @Override
    public int [] textToSequence(String text) {
        final Integer[][] sequences = kerasTokenizer.textsToSequences(new String[]{text});
        Preconditions.checkState(sequences.length == 1, "Expecting batch of one");
        return ArrayUtils.toPrimitive(sequences[0]);
    }

}

