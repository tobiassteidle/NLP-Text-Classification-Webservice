package de.codecave.demo.component.impl;

import com.google.common.base.Preconditions;
import de.codecave.demo.component.StemmerService;
import de.codecave.demo.component.TextCleanerService;
import de.codecave.demo.component.TextPreprocessor;
import de.codecave.demo.util.Python3CompatUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class TextCleanerServiceImpl implements TextCleanerService {

    @Autowired
    private StemmerService stemmerService;

    private Set<String> stopWords;

    @PostConstruct
    public void init() {
        this.stopWords = loadStopWordsFromTxtFile();
    }

    @Override
    public String cleanText(String text) {
        final Pattern REGEX_WHITESPACE = Pattern.compile("\\s");

        return
                REGEX_WHITESPACE.splitAsStream(text)
                        .map(tok -> tok.toLowerCase(Locale.ENGLISH))
                        .map(tok -> stemmerService.tryStemming(tok).orElse(tok))
                        .map(String::trim) // python3 string.strip The strip() method removes any whitespace from the beginning or the end:
                        .map(tok -> removePunctuation(tok))
                        .map(tok -> Python3CompatUtil.isnumeric(tok) ? "<num>" : tok)
                        .filter(tok -> !stopWords.contains(tok))
                        .collect(Collectors.joining(" "));
    }

    private static String removePunctuation(String text) {
        final String punctuations = Pattern.quote("!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}‘’");
        return text.replaceAll("[" + punctuations + "]", "");
    }

    private Set<String> loadStopWordsFromTxtFile() {
        final InputStream is = TextPreprocessor.class.getResourceAsStream("/nlp/stopwords.txt");
        Preconditions.checkNotNull("stopwords file not found");
        return
                new BufferedReader(new InputStreamReader(
                        is)).lines()
                        .peek(word -> Preconditions.checkState(StringUtils.isNotBlank(word)))
                        .collect(Collectors.toSet());
    }

}
