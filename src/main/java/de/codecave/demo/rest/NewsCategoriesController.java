package de.codecave.demo.rest;

import de.codecave.demo.component.NewsCategoriesService;
import de.codecave.demo.rest.dto.PredictionRequest;
import de.codecave.demo.rest.dto.PredictionResponse;
import one.util.streamex.EntryStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        final String bestClass = EntryStream.of(predictions)
                .maxByDouble(entry -> entry.getValue())
                .orElseThrow(() -> new IllegalStateException())
                .getKey();

        final PredictionResponse response = new PredictionResponse();
        response.setNewsLine(newsLine);
        response.setPredictions(predictions);
        response.setBestClass(bestClass);

        return ResponseEntity.ok(response);
    }


}
