package de.codecave.demo.component;

import de.codecave.demo.component.impl.TextPreprocessorImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TextPreprocessorTest {

    private static final String TEST_SENTENCE = "Trump Paid More Than $100,000 To Cohen, Financial Disclosure Confirms";

    private static TextPreprocessor textPreprocessor;

    @BeforeAll
    static void initialize() {
        textPreprocessor = new TextPreprocessorImpl();
    }

    @Test
    public void cleanTextTest() {
        assertThat(textPreprocessor.cleanText(TEST_SENTENCE), is("trump paid #num# cohen financial disclosure confirms"));
    }

    @Test
    public void tokenizeTest() {
        assertThat(textPreprocessor.tokenize(TEST_SENTENCE), is(new int[]{1, 2, 3, 4, 5, 6, 7}));
    }

    @Test
    public void paddingTest() {
        assertThat(textPreprocessor.padding(textPreprocessor.tokenize(TEST_SENTENCE)), is(new int[]{1, 2, 3, 4, 5, 6, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
    }

    @Test
    public void preprocessingPipelineTest() {
        assertThat(textPreprocessor.pipeline(TEST_SENTENCE), is("Y"));
    }
}
