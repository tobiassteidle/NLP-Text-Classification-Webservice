package de.codecave.demo.component.blackbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.codecave.demo.component.ModelMetadata;
import de.codecave.demo.component.NewsCategoriesService;
import one.util.streamex.EntryStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class PredictionBlackboxTest {

    private static final float DELTA_FLOAT = 0.000001f;

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

            if (equalsFloatMap(actual, expected, DELTA_FLOAT)) {
                System.out.println("-> OK");
                success++;
            } else {
                System.out.println("-> ERR - exp " + expected);
                System.out.println("       - got " + actual);
                error++;
            }

            System.out.println();
        }

        System.out.println("Successes: " + success);
        assertThat(error, is(0));
    }

    private BlackboxTestData loadModelFile() {

        try {

            return objectMapper.readValue(blackboxTestFile.getInputStream(), BlackboxTestData.class);

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

    /**
     * 0.97438747 vs. 0.97438735
     */
    private static boolean equalsFloatMap(
            final Map<String, Float> first, final Map<String, Float> second, final float delta) {

        if (first.size() != second.size()) {
            return false;
        }

        return first.entrySet().stream()
                .allMatch(e -> floatIsEqual(e.getValue(), second.get(e.getKey()), delta));

    }

    static private boolean floatIsEqual(float f1, float f2, float delta) {
        if (Float.compare(f1, f2) == 0) {
            return true;
        }
        if ((Math.abs(f1 - f2) <= delta)) {
            return true;
        }

        return false;
    }

}
