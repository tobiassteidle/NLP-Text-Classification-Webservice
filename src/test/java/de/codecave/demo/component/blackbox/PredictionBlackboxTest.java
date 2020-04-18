package de.codecave.demo.component.blackbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.primitives.Ints;
import de.codecave.demo.component.ModelMetadata;
import de.codecave.demo.component.NewsCategoriesService;
import de.codecave.demo.component.TensorflowService;
import de.codecave.demo.component.impl.*;
import de.codecave.demo.spring.Configuration;
import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(properties = {"tensorflow.model_dir=../model/tensorflow3/tensorflow"})
public class PredictionBlackboxTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("classpath:/nlp/blackbox_test.json")
    private Resource blackboxTestFile;

    @Autowired
    private NewsCategoriesService newsCategoriesService;

    @Value("classpath:/model/model_metadata.json")
    private Resource modelDataJsonFile;

    private ModelMetadata modelMetadata() {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(modelDataJsonFile.getInputStream(), ModelMetadata.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Test
    void testTextPreprocessing() {
        int success = 0;
        int error = 0;

        final ModelMetadata modelMetadata = modelMetadata();
        final BlackboxTestData testData = loadModelFile();

        for (BlackboxTestData.Sentence sentence : testData.getTestSentences()) {

            System.out.println("input\t" + sentence.getSentence());
            System.out.println("tok\t" + sentence.getTokenized());

            final Map<String, Float> actual = newsCategoriesService.predictCategories(sentence.getSentence());

            final Map<String, Float> expected =
                EntryStream.zip(modelMetadata.getClasses(), sentence.getPredicted())
                    .toImmutableMap();

            if (actual.equals(expected)) {
                System.out.println("-> OK");
                success++;
            } else {
                System.out.println("-> ERR - exp " + expected);
                System.out.println("       - got " + actual);
                error++;
            }

            System.out.println();
        }

        assertThat(error, is(0));
    }

    private BlackboxTestData loadModelFile() {

        try {

            return objectMapper.readValue(blackboxTestFile.getInputStream(), BlackboxTestData.class);

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

}
