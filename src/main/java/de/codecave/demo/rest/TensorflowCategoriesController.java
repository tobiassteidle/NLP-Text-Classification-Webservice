package de.codecave.demo.rest;

import de.codecave.demo.component.PredictionService;
import de.codecave.demo.rest.dto.PredictionRequest;
import de.codecave.demo.rest.dto.PredictionResponse;
import one.util.streamex.EntryStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/tensorflow")
public class TensorflowCategoriesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TensorflowCategoriesController.class);

    @Autowired
    private PredictionService predictionService;

    @PostMapping("/categories")
    public ResponseEntity predictCategoriesForNewsLine(
            @RequestBody @Valid PredictionRequest request
    ) {
        final String newsLine = request.getNewsLine();

        final Map<Float, String> predictions = predictionService.predictCategories(newsLine);

        final String maxCategory =
            predictionService.findMaxCategory(predictions);

        final boolean confident = predictionService.confidence(predictions);

        final PredictionResponse response = new PredictionResponse();
        response.setNewsLine(newsLine);
        response.setPredictions(predictions);
        response.setMaxCategory(maxCategory);
        response.setConfident(confident);

        return ResponseEntity.ok(response);
    }


}
