package de.codecave.demo.component;

import de.codecave.demo.component.impl.TextPreprocessorImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TextPreprocessorTest {

    private static final String TEST_SENTENCE_1 = "Will Ferrell And Molly Shannon Cover The Royal Wedding As \'Cord And Tish\'";
    private static final String TEST_SENTENCE_2 = "7 Fashion Mistakes You\'ll Regret Forever";
    // TODO discuss App vs. Apps lemmatization
    private static final String TEST_SENTENCE_3 = "Best Travel App And Hacks For Your Vacation Workout";

    private static TextPreprocessor textPreprocessor;

    @BeforeAll
    static void initialize() {
        textPreprocessor = new TextPreprocessorImpl();
    }

    @Test
    public void cleanTextTest() {
        assertThat(textPreprocessor.cleanText(TEST_SENTENCE_1), is("ferrell molly shannon cover royal wedding cord tish"));
        assertThat(textPreprocessor.cleanText(TEST_SENTENCE_2), is("#num# fashion mistake youll regret forever"));
        assertThat(textPreprocessor.cleanText(TEST_SENTENCE_3), is("best travel app hack vacation workout"));
    }

    @Test
    public void tokenizeTest() {
        assertThat(textPreprocessor.tokenize("ferrell molly shannon cover royal wedding cord tish"), is(new int[]{266, 754, 584}));
        assertThat(textPreprocessor.tokenize("#num# fashion mistake youll regret forever"), is(new int[]{1, 64, 520, 739, 1692, 1508}));
        assertThat(textPreprocessor.tokenize("best travel app hack vacation workout"), is(new int[]{12, 56, 1310, 1312, 307, 328}));
    }

    @Test
    public void paddingTest() {
        assertThat(textPreprocessor.padding(new int[]{266, 754, 584}), is(new int[]{266, 754, 584, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
        assertThat(textPreprocessor.padding(new int[]{1, 64, 520, 739, 1692, 1508}), is(new int[]{1, 64, 520, 739, 1692, 1508, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
        assertThat(textPreprocessor.padding(new int[]{12, 56, 1310, 1312, 307, 328}), is(new int[]{12, 56, 1310, 1312, 307, 328, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
    }

    @Test
    public void preprocessingPipelineTest() {
        assertThat(textPreprocessor.pipeline(TEST_SENTENCE_1), is(new int[]{266, 754, 584, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
        assertThat(textPreprocessor.pipeline(TEST_SENTENCE_2), is(new int[]{1, 64, 520, 739, 1692, 1508, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
        assertThat(textPreprocessor.pipeline(TEST_SENTENCE_3), is(new int[]{12, 56, 1310, 1312, 307, 328, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
    }
}
