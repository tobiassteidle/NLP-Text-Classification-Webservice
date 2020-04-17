package de.codecave.demo.rest;

import de.codecave.demo.component.NewsCategoriesService;
import de.codecave.demo.rest.dto.PredictionRequest;
import de.codecave.demo.rest.dto.PredictionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/news-category")
public class NewsCategoriesController {

    @Autowired
    private NewsCategoriesService predictionService;

    @PostMapping("/prediction")
    public ResponseEntity predictCategoriesForNewsLine(
            @RequestBody @Valid PredictionRequest request
    ) {
        final String newsLine = request.getNewsLine();

        final Map<String, Float> predictions = predictionService.predictCategories(newsLine);

        final PredictionResponse response = new PredictionResponse();
        response.setNewsLine(newsLine);
        response.setPredictions(predictions);

        return ResponseEntity.ok(response);
    }


}
