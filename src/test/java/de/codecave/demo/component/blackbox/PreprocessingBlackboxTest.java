package de.codecave.demo.component.blackbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import de.codecave.demo.component.TextPreprocessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * test against input-output data from python
 */
@SpringBootTest
public class PreprocessingBlackboxTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private TextPreprocessor textPreprocessor;


    @Test
    void testTokenizer() {
        int success = 0;
        int error = 0;

        final ModelFile modelFile = loadModelFile();

        for(ModelFile.Sentence sentence : modelFile.getTestSentences()) {

            System.out.println("input\t" + sentence.getSentence());
            System.out.println("tok\t" + sentence.getTokenized());

            final String cleaned = textPreprocessor.cleanText(sentence.getSentence());
            System.out.println("clean\t" + cleaned);

            final List<Integer> actual = Ints.asList(textPreprocessor.tokenize(
                    cleaned));
            final List<Integer> expected = sentence.getTokenized();
            if (actual.equals(expected)) {
                System.out.println("-> OK");
                success++;
            } else {
                System.out.println("-> ERR - got " + actual);
                error++;
            }

            System.out.println();
        }

        assertThat(error, is(0));
    }

    private ModelFile loadModelFile() {

        try (InputStream is = PreprocessingBlackboxTest.class
                .getResourceAsStream("/modeldata.json")) {
            Preconditions.checkNotNull(is);

                return objectMapper.readValue(is, ModelFile.class);

        } catch (IOException e) {
            throw new  IllegalStateException(e);
        }

    }
}
