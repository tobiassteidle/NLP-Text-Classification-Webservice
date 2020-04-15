package de.codecave.demo.rest.dto;

import java.util.Map;

public class PredictionResponse {

    private String newsLine;
    private Map<Float, String> predictions;
    private String maxCategory;
    private boolean confident;

    public String getNewsLine() {
        return newsLine;
    }

    public void setNewsLine(final String newsLine) {
        this.newsLine = newsLine;
    }

    public Map<Float, String> getPredictions() {
        return predictions;
    }

    public void setPredictions(final Map<Float, String> predictions) {
        this.predictions = predictions;
    }

    public String getMaxCategory() {
        return maxCategory;
    }

    public void setMaxCategory(final String maxCategory) {
        this.maxCategory = maxCategory;
    }

    public boolean isConfident() {
        return confident;
    }

    public void setConfident(final boolean confident) {
        this.confident = confident;
    }
}
