package de.codecave.demo.rest.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PredictionRequest {

    @NotNull
    private String newsLine;

    public String getNewsLine() {
        return newsLine;
    }

    public void setNewsLine(final String newsLine) {
        this.newsLine = newsLine;
    }

}
