package de.codecave.demo.component.blackbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.primitives.Ints;
import de.codecave.demo.component.TextCleanerService;
import de.codecave.demo.component.TextPreprocessor;
import de.codecave.demo.component.impl.KerasTokenizerImpl;
import de.codecave.demo.component.impl.SimplePaddingServiceImpl;
import de.codecave.demo.component.impl.StemmerServiceImpl;
import de.codecave.demo.component.impl.TextCleanerServiceImpl;
import de.codecave.demo.component.impl.TextPreprocessorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * test against input-output data from python
 */
@ContextConfiguration(classes = {
        TextPreprocessorImpl.class,
        KerasTokenizerImpl.class,
        SimplePaddingServiceImpl.class,
        StemmerServiceImpl.class,
        TextCleanerServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class PreprocessingBlackboxTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private TextPreprocessor textPreprocessor;

    @Autowired
    private TextCleanerService textCleanerService;

    @Value("classpath:/nlp/blackbox_test.json")
    private Resource blackboxTestFile;

    @Test
    void testTextPreprocessing() {
        int successCount = 0;
        int errorCount = 0;

        final BlackboxTestData testData = loadModelFile();

        for (BlackboxTestData.Sentence sentence : testData.getTestSentences()) {

            System.out.println("given\t\t" + sentence.getSentence());
            System.out.println("expected\t" + sentence.getTokenized());

            final String cleaned = textCleanerService.cleanText(sentence.getSentence());
            System.out.println("expected\t" + cleaned);

            final List<Integer> actual = Ints.asList(textPreprocessor.pipeline(sentence.getSentence()));
            final List<Integer> expected = sentence.getPadded().stream().map(Float::intValue).collect(Collectors.toList());
            if (actual.equals(expected)) {
                System.out.println("-> OK");
                successCount++;
            } else {
                System.out.println("-> MISMATCH");
                System.out.println("got\t\t" + textCleanerService.cleanText(sentence.getSentence()));
                System.out.println("got\t\t" + actual);
                errorCount++;
            }

            System.out.println();
        }

        System.out.println(String.format("%d successes and %d errors", successCount, errorCount));
        assertThat(errorCount, is(0));
    }

    private BlackboxTestData loadModelFile() {

        try {

            return objectMapper.readValue(blackboxTestFile.getInputStream(), BlackboxTestData.class);

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

}
