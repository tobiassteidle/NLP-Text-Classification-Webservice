package de.codecave.demo.component;

import de.codecave.demo.component.impl.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ContextConfiguration(classes = {
        TextPreprocessorImpl.class,
        KerasTokenizerImpl.class,
        SimplePaddingServiceImpl.class,
        StemmerServiceImpl.class,
        TextCleanerServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class TextPreprocessorTest {

    private static final String TEST_SENTENCE_1 = "Will Ferrell And Molly Shannon Cover The Royal Wedding As \'Cord And Tish\'";
    private static final String TEST_SENTENCE_2 = "7 Fashion Mistakes You\'ll Regret Forever";
    private static final String TEST_SENTENCE_3 = "Best Travel Apps And Hacks For Your Vacation Workout";

    @Autowired
    private TextPreprocessor textPreprocessor;

    @Autowired
    private TokenizerService tokenizerService;

    @Autowired
    private PaddingService paddingService;

    @Autowired
    private TextCleanerService textCleanerService;

    @Test
    public void cleanTextTest() {
        assertThat(textCleanerService.cleanText(TEST_SENTENCE_1), is("ferrel molli shannon cover royal wed cord tish"));
        assertThat(textCleanerService.cleanText(TEST_SENTENCE_2), is("<num> fashion mistak youll regret forev"));
        assertThat(textCleanerService.cleanText(TEST_SENTENCE_3), is("best travel app hack vacat workout"));
    }

    @Test
    public void paddingTest() {
        assertThat(paddingService.padding(new int[]{11, 22, 33}), is(new int[]{11, 22, 33, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
    }

}
