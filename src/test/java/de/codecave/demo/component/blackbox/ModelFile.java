package de.codecave.demo.component.blackbox;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ModelFile {

    private List<String> classes;

    @JsonProperty("test_sentences")
    private List<Sentence> testSentences;

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(final List<String> classes) {
        this.classes = classes;
    }

    public List<Sentence> getTestSentences() {
        return testSentences;
    }

    public void setTestSentences(final List<Sentence> testSentences) {
        this.testSentences = testSentences;
    }

    static class Sentence {

        private List<String> cleaned;

        // TODO double?
        private List<Double> padded;
        private List<List<Double>> predicted;
        private List<Double> preprocessed;
        private String sentence;
        private List<Integer> tokenized;

        public List<String> getCleaned() {
            return cleaned;
        }

        public void setCleaned(final List<String> cleaned) {
            this.cleaned = cleaned;
        }

        public List<Double> getPadded() {
            return padded;
        }

        public void setPadded(final List<Double> padded) {
            this.padded = padded;
        }

        public List<List<Double>> getPredicted() {
            return predicted;
        }

        public void setPredicted(final List<List<Double>> predicted) {
            this.predicted = predicted;
        }

        public List<Double> getPreprocessed() {
            return preprocessed;
        }

        public void setPreprocessed(final List<Double> preprocessed) {
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
