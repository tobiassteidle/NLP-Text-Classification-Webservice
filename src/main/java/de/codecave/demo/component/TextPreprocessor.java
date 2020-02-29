package de.codecave.demo.component;

public interface TextPreprocessor {
    String cleanText(String text);

    int [] tokenize(String text);

    int [] padding(int [] tokenizedText);

    int [] pipeline(String text);
}
