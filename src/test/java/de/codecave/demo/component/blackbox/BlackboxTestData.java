package de.codecave.demo.component.blackbox;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BlackboxTestData {

    @JsonProperty("test_sentences")
    private List<Sentence> testSentences;

    public List<Sentence> getTestSentences() {
        return testSentences;
    }

    public void setTestSentences(final List<Sentence> testSentences) {
        this.testSentences = testSentences;
    }

    static class Sentence {

        private List<String> cleaned;

        private List<Float> padded;
        private List<Float> predicted;
        private List<Float> preprocessed;
        private String sentence;
        private List<Integer> tokenized;

        public List<String> getCleaned() {
            return cleaned;
        }

        public void setCleaned(final List<String> cleaned) {
            this.cleaned = cleaned;
        }

        public List<Float> getPadded() {
            return padded;
        }

        public void setPadded(final List<Float> padded) {
            this.padded = padded;
        }

        public List<Float> getPredicted() {
            return predicted;
        }

        public void setPredicted(final List<Float> predicted) {
            this.predicted = predicted;
        }

        public List<Float> getPreprocessed() {
            return preprocessed;
        }

        public void setPreprocessed(final List<Float> preprocessed) {
            this.preprocessed = preprocessed;
        }

        public String getSentence() {
            return sentence;
        }

        public void setSentence(final String sentence) {
            this.sentence = sentence;
        }

        public List<Integer> getTokenized() {
            return tokenized;
        }

        public void setTokenized(final List<Integer> tokenized) {
            this.tokenized = tokenized;
        }
    }

}
