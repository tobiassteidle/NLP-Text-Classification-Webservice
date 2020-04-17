package de.codecave.demo.component.impl;

import com.google.common.base.Preconditions;
import de.codecave.demo.component.TokenizerService;
import org.apache.commons.lang3.ArrayUtils;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.preprocessing.text.KerasTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class KerasTokenizerImpl implements TokenizerService {

    private static final int MAX_FEATURES = 2000;

    private KerasTokenizer kerasTokenizer;

    @Value("classpath:/nlp/tokenizer.json")
    private Resource tokenizerJsonFile;

    @PostConstruct
    public void init() {
        kerasTokenizer = loadAndConfigure();
    }

    private KerasTokenizer loadAndConfigure() {
        try {
            final Path tmpFile = Files.createTempFile("keras_tokenizer_config", ".json");

            Files.copy(tokenizerJsonFile.getInputStream(), tmpFile, StandardCopyOption.REPLACE_EXISTING);
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

    @Override
    public int[] textToSequence(String text) {
        final Integer[][] sequences = kerasTokenizer.textsToSequences(new String[]{text});
        Preconditions.checkState(sequences.length == 1, "Expecting batch of one");
        return ArrayUtils.toPrimitive(sequences[0]);
    }

}

