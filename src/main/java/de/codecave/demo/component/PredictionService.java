package de.codecave.demo.component;

import java.util.Map;

public interface PredictionService {

    Map<Float, String> predictCategories(String newsLine);

    String findMaxCategory(Map<Float, String> predictions);

    boolean confidence(Map<Float, String> predictions);

}
