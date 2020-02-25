package de.codecave.demo.component;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class TextPreprocessorTest {

    private static final String TEST_SENTENCE = "Trump Paid More Than $100,000 To Cohen, Financial Disclosure Confirms";

    @Autowired
    private TextPreprocessor textPreprocessor;

    @Test
    public void cleanTextTest() {
        assertThat(textPreprocessor.cleanText(TEST_SENTENCE), is("trump paid #num# cohen financial disclosure confirms"));
    }

    @Test
    public void tokenizeTest() {
        //[[1, 2, 3, 4, 5, 6, 7]]
    }

    @Test
    public void paddingTest() {
        //[[1., 2., 3., 4., 5., 6., 7., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.]]
    }

    @Test
    public void preprocessingPipelineTest() {

    }
}
/*
def preprocess(x, tokenizer=None, padding=None):
    """
    Preprocess x
    :param x: Feature List of sentences
    :return: Tuple of (Preprocessed x, x tokenizer)
    """
    preprocess_x = clean_text(x)

    preprocess_x, _ = tokenize(preprocess_x, tokenizer)
    preprocess_x, pad_length = pad(preprocess_x, length=padding)

    return preprocess_x, pad_length
 */
