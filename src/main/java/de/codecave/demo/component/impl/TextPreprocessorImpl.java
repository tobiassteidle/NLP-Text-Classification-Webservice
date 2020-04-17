package de.codecave.demo.component.impl;

import de.codecave.demo.component.PaddingService;
import de.codecave.demo.component.TextCleanerService;
import de.codecave.demo.component.TextPreprocessor;
import de.codecave.demo.component.TokenizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TextPreprocessorImpl implements TextPreprocessor {

    @Autowired
    private TokenizerService tokenizerService;

    @Autowired
    private PaddingService paddingService;

    @Autowired
    private TextCleanerService textCleanerService;

    @Override
    public int[] pipeline(String text) {
        return paddingService.padding(
                tokenizerService.textToSequence(
                        textCleanerService.cleanText(text)));
    }

}
