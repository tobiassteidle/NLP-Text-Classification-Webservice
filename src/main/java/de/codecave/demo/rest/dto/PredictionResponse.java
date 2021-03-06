package de.codecave.demo.rest.dto;

import java.util.Map;

public class PredictionResponse {

    private String newsLine;
    private Map<String, Float> predictions;
    private String bestClass;

    public String getNewsLine() {
        return newsLine;
    }

    public void setNewsLine(final String newsLine) {
        this.newsLine = newsLine;
    }

    public Map<String, Float> getPredictions() {
        return predictions;
    }

    public void setPredictions(final Map<String, Float> predictions) {
        this.predictions = predictions;
    }

    public String getBestClass() {
        return bestClass;
    }

    public void setBestClass(final String bestClass) {
        this.bestClass = bestClass;
    }
}
